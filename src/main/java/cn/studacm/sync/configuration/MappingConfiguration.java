package cn.studacm.sync.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * 〈功能简述〉<br>
 * 〈加载自定义配置文件--映射关联〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Configuration
public class MappingConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        // class引入，避免了路径处理问题
        yaml.setResources(new ClassPathResource("mapping.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
