package cn.studacm.sync.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

@Component
public interface BaseDao {

    Map<String, Object> selectByPK(@Param("key") String key, @Param("value") Object value, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Map<String, Object>> selectByPKs(@Param("key") String key, @Param("valueList") List<Object> valueList, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Map<String, Object>> selectByPKsLockInShareMode(@Param("key") String key, @Param("valueList") List<Object> valueList, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    Long count(@Param("key") String key, @Param("databaseName") String databaseName, @Param("tableName") String tableName, @Param("minPK") long minPK, @Param("maxPK") long maxPK);

    Long selectMaxPK(@Param("key") String key, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    Long selectMinPK(@Param("key") String key, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Map<String, Object>> selectByPKInterval(@Param("key") String key, @Param("minPK") long minPK, @Param("maxPK") long maxPK, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

    List<Map<String, Object>> selectByPKIntervalLockInShareMode(@Param("key") String key, @Param("minPK") long minPK, @Param("maxPK") long maxPK, @Param("databaseName") String databaseName, @Param("tableName") String tableName);

}
