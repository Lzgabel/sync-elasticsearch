package cn.studacm.sync.listener;

import cn.studacm.sync.util.BeanKit;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import cn.studacm.sync.event.InsertCanalEvent;
import cn.studacm.sync.service.IElasticsearchService;
import cn.studacm.sync.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Slf4j
@Component
public class InsertCanalListener extends AbstractCanalListener<InsertCanalEvent> {


    @Override
    protected void doSync(String database, String table, String index, String type, RowData rowData) {
        List<Column> columns = rowData.getAfterColumnsList();
        String primaryKey = getPrimaryKey(database, table);
        Column idColumn = columns.stream().filter(column -> column.getIsKey() && primaryKey.equals(column.getName())).findFirst().orElse(null);
        if (idColumn == null || StringUtils.isBlank(idColumn.getValue())) {
            log.warn("insert_column_find_null_warn insert从column中找不到主键,database=" + database + ",table=" + table);
            return;
        }
        log.debug("insert_column_id_info insert主键id,database=" + database + ",table=" + table + ",id=" + idColumn.getValue());

        // 同步 es
        Map<String, Object> dataMap = parseColumnsToMap(columns);
        IElasticsearchService elasticsearchService = getElasticsearchService(database, table);
        elasticsearchService.insertById(index, type, idColumn.getValue(), dataMap);

        log.debug("insert_es_info 同步es插入操作成功！database=" + database + ",table=" + table + ",data=" + JsonUtil.toJson(dataMap));
    }
}
