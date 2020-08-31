package cn.studacm.sync.listener;

import cn.studacm.sync.event.DeleteCanalEvent;
import cn.studacm.sync.service.IElasticsearchService;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class DeleteCanalListener extends AbstractCanalListener<DeleteCanalEvent> {

    @Autowired
    private IElasticsearchService IElasticsearchService;

    @Override
    protected void doSync(String database, String table, String index, String type, RowData rowData) {
        List<Column> columns = rowData.getBeforeColumnsList();
        String primaryKey = getPrimaryKey(database, table);
        Column idColumn = columns.stream().filter(column -> column.getIsKey() && primaryKey.equals(column.getName())).findFirst().orElse(null);
        if (idColumn == null || StringUtils.isBlank(idColumn.getValue())) {
            log.warn("delete_column_find_null_warn insert从column中找不到主键,database=" + database + ",table=" + table);
            return;
        }
        log.debug("delete_column_id_info insert主键id,database=" + database + ",table=" + table + ",id=" + idColumn.getValue());
        // 删除 es 数据
        IElasticsearchService.deleteById(index, type, idColumn.getValue());
        log.debug("delete_es_info 同步es插入操作成功！database=" + database + ",table=" + table + ",id=" + idColumn.getValue());
    }
}
