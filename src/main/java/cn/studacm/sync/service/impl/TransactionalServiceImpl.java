package cn.studacm.sync.service.impl;

import cn.studacm.sync.configuration.IndexProperties;
import cn.studacm.sync.model.request.SyncByTableRequest;
import cn.studacm.sync.service.IElasticsearchService;
import cn.studacm.sync.service.ITransactionalService;
import cn.studacm.sync.dao.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
@Service
public class TransactionalServiceImpl implements ITransactionalService {

    @Resource
    private BaseDao baseDao;

    @Resource
    private IElasticsearchService IElasticsearchService;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public void batchInsertElasticsearch(SyncByTableRequest request, String primaryKey, long from, long to, IndexProperties indexProperties) {
        List<Map<String, Object>> dataList = baseDao.selectByPKIntervalLockInShareMode(primaryKey, from, to, request.getDatabase(), request.getTable());
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        IElasticsearchService.batchInsertById(indexProperties.getIndex(), indexProperties.getType(), dataList);
    }

    private List<Map<String, Object>> convertDateType(List<Map<String, Object>> source) {
        source.parallelStream().forEach(map -> map.forEach((key, value) -> {
            if (value instanceof Timestamp) {
                map.put(key, LocalDateTime.ofInstant(((Timestamp) value).toInstant(), ZoneId.systemDefault()));
            }
        }));
        return source;
    }
}
