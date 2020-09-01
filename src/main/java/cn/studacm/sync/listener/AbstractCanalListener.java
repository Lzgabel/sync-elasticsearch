package cn.studacm.sync.listener;

import cn.studacm.sync.service.IElasticsearchService;
import cn.studacm.sync.util.BeanKit;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.google.protobuf.InvalidProtocolBufferException;
import cn.studacm.sync.configuration.IndexProperties;
import cn.studacm.sync.configuration.MappingProperties;
import cn.studacm.sync.event.AbstractCanalEvent;
import cn.studacm.sync.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractCanalListener<EVENT extends AbstractCanalEvent> implements ApplicationListener<EVENT>, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCanalListener.class);

    @Resource
    private MappingProperties mappingProperties;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Map<String, Function<String, Object>> mysqlTypeElasticsearchTypeMapping;

    @Override
    public void onApplicationEvent(EVENT event) {
        Entry entry = event.getEntry();
        String database = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();

        IndexProperties indexProperties = mappingProperties.get(database, table);
        if (indexProperties == null) {
            log.warn("canal 收到数据变更, 配置文件中缺失对应配置信息, 忽略当前变更, database={}, table={}", database, table);
            return;
        }
        String index = indexProperties.getIndex();
        String type = indexProperties.getType();
        RowChange change;
        try {
            change = RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            logger.error("canalEntry_parser_error,根据CanalEntry获取RowChange失败！", e);
            return;
        }
        change.getRowDatasList().forEach(rowData -> doSync(database, table, index, type, rowData));
    }

    Map<String, Object> parseColumnsToMap(List<Column> columns) {
        Map<String, Object> jsonMap = new HashMap<>();
        columns.forEach(column -> {
            if (column == null) {
                return;
            }
            // 数据库表字段驼峰转换
            String key = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getName());
            jsonMap.put(key, column.getIsNull() ? null : convertToElasticsearchType(column.getMysqlType(), column.getValue()));
        });
        return jsonMap;
    }

    /**
     * mysql type cover to elasticsearch type
     *
     * @param mysqlType
     * @param data
     * @return
     */
    private Object convertToElasticsearchType(String mysqlType, String data) {
        Optional<Map.Entry<String, Function<String, Object>>> result = mysqlTypeElasticsearchTypeMapping.entrySet().parallelStream().filter(entry -> mysqlType.toLowerCase().contains(entry.getKey())).findFirst();
        return result.isPresent() ? result.get().getValue().apply(data) : data;
    }

    @Override
    public void afterPropertiesSet() {
        mysqlTypeElasticsearchTypeMapping = Maps.newHashMap();
        mysqlTypeElasticsearchTypeMapping = Maps.newHashMap();
        mysqlTypeElasticsearchTypeMapping.put("char", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("text", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("blob", data -> data);
        mysqlTypeElasticsearchTypeMapping.put("int", Long::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("date", data -> LocalDateTime.parse(data, FORMATTER));
        mysqlTypeElasticsearchTypeMapping.put("time", data -> LocalDateTime.parse(data, FORMATTER));
        mysqlTypeElasticsearchTypeMapping.put("float", Double::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("double", Double::valueOf);
        mysqlTypeElasticsearchTypeMapping.put("decimal", Double::valueOf);
    }

    String getPrimaryKey(String database, String table) {
        return Optional.ofNullable(mappingProperties.get(database, table)).map(IndexProperties::getPrimaryKey).orElse(null);
    }

    IElasticsearchService getElasticsearchService(String database, String table) {
        String processBeanName = Optional.ofNullable(mappingProperties.get(database, table)).map(IndexProperties::getProcessBeanName).orElse(null);
        IElasticsearchService elasticsearchService = BeanKit.getBean(processBeanName, IElasticsearchService.class);
        if (Objects.isNull(elasticsearchService)) {
            throw new RuntimeException(String.format("没有找到对应的 bean: %s", processBeanName));
        }
        return elasticsearchService;
    }

    /**
     * 同步 es
     *
     * @param database
     * @param table
     * @param index
     * @param type
     * @param rowData
     */
    protected abstract void doSync(String database, String table, String index, String type, RowData rowData);
}
