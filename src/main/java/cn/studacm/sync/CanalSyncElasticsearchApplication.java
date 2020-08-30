package cn.studacm.sync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈功能简述〉<br>
 * 〈我就试试〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@RestController
@MapperScan("cn.studacm.sync.dao")
public class CanalSyncElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalSyncElasticsearchApplication.class, args);
    }

}
