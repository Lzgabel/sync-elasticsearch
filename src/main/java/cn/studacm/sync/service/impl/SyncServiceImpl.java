package cn.studacm.sync.service.impl;

import cn.studacm.sync.configuration.IndexProperties;
import cn.studacm.sync.configuration.MappingProperties;
import cn.studacm.sync.model.request.SyncByTableRequest;
import cn.studacm.sync.service.SyncService;
import cn.studacm.sync.service.TransactionalService;
import cn.studacm.sync.dao.BaseDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Slf4j
@Service
public class SyncServiceImpl implements SyncService, InitializingBean, DisposableBean {
    /**
     * 使用线程池控制并发数量
     */
    private ExecutorService cachedThreadPool;

    @Resource
    private BaseDao baseDao;


    @Resource
    private TransactionalService transactionalService;

    @Autowired
    MappingProperties properties;

    @Override
    public boolean syncByTable(SyncByTableRequest request) {
        IndexProperties indexProperties = properties.get(request.getDatabase(), request.getTable());
        if (Objects.isNull(indexProperties)) {
            log.error("批量转换并插入Elasticsearch异常, 没有找到该映射配置, database={}, table={}", request.getDatabase(), request.getTable());
            throw new IllegalArgumentException(String.format("配置文件中缺失database=%s和table=%s所对应的index和type的映射配置", request.getDatabase(), request.getTable()));
        }

        String primaryKey = indexProperties.getPrimaryKey();

        long minPK = Optional.ofNullable(request.getFrom()).orElse(baseDao.selectMinPK(primaryKey, request.getDatabase(), request.getTable()));
        long maxPK = Optional.ofNullable(request.getTo()).orElse(baseDao.selectMaxPK(primaryKey, request.getDatabase(), request.getTable()));
        long total = baseDao.count(primaryKey, request.getDatabase(), request.getTable(), minPK, maxPK);
        cachedThreadPool.submit(() -> {
            try {
                long count = minPK > total ? minPK + total : total;
                for (long i = minPK; i < count; i += request.getStepSize()) {
                    transactionalService.batchInsertElasticsearch(request, primaryKey, i, i + request.getStepSize()-1, indexProperties);
                    log.info("当前同步pk={}，stepSize={}, 总共total={}", i, request.getStepSize(), total);
                }
                log.info("同步完成");
            } catch (Exception e) {
                log.error("批量转换并插入Elasticsearch异常", e);
            }
        });
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cachedThreadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), (ThreadFactory) Thread::new);
    }

    @Override
    public void destroy() throws Exception {
        if (cachedThreadPool != null) {
            cachedThreadPool.shutdown();
        }
    }
}
