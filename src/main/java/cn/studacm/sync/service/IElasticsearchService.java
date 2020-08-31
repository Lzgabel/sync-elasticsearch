package cn.studacm.sync.service;

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
public interface IElasticsearchService {
    void insertById(String index, String type, String id, Map<String, Object> dataMap);

    void batchInsertById(String index, String type, List<Map<String, Object>> dataList);

    void update(String index, String type, String id, Map<String, Object> dataMap);

    void deleteById(String index, String type, String id);
}
