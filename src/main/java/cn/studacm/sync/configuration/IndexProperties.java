package cn.studacm.sync.configuration;

import lombok.Data;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Data
public class IndexProperties {

    private String index;

    private String type;

    private String primaryKey;

    private String processBeanName;

}
