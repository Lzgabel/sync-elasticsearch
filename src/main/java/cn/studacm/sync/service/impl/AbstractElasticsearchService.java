package cn.studacm.sync.service.impl;

import cn.studacm.sync.service.IElasticsearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
@Service
public abstract class AbstractElasticsearchService implements IElasticsearchService {

    @Autowired
    JestClient jestClient;

    @Override
    public abstract void insertById(String index, String type, String id, Map<String, Object> dataMap);

    @Override
    public abstract void batchInsertById(String index, String type, List<Map<String, Object>> dataList);

    @Override
    public void update(String index, String type, String id, Map<String, Object> dataMap) {
        this.insertById(index, type, id, dataMap);
    }

    @Override
    public void deleteById(String index, String type, String id) {
        Delete.Builder  builder = new Delete.Builder(id);
        Delete delete = builder.index(index).type(type).id(id).build();
        try {
            jestClient.execute(delete);
        } catch (IOException e) {
            log.error("删除文档失败", e);
        }
    }
}
