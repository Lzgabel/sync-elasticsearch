package cn.studacm.sync.configuration;

import cn.studacm.sync.util.BeanKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-31
 * @since 1.0.0
 */

@Configuration
public class BeanKitConfiguration {

    @Bean
    public BeanKit beanKit() {
        return new BeanKit();
    }
}
