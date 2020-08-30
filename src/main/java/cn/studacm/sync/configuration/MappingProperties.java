package cn.studacm.sync.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Setter
@Configuration
@ConfigurationProperties(prefix = "index")
public class MappingProperties {
    private Map<String, Map<String, IndexProperties>> mappings;

    /**
     * 获取索引配置
     * @param schema
     * @param table
     * @return res or null
     */
    public IndexProperties get(String schema, String table) {
        return Optional.ofNullable(mappings.get(schema)).map(v -> v.get(table)).orElse(null);
    }
}
