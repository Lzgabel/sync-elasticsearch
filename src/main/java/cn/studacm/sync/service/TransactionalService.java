package cn.studacm.sync.service;

import cn.studacm.sync.configuration.IndexProperties;
import cn.studacm.sync.model.request.SyncByTableRequest;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
public interface TransactionalService {

    /**
     * 开启事务的读取mysql并插入到Elasticsearch中（读锁）
     */
    void batchInsertElasticsearch(SyncByTableRequest request, String primaryKey, long from, long to, IndexProperties indexProperties);
}
