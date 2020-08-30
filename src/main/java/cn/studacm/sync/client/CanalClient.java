package cn.studacm.sync.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Slf4j
@Configuration
public class CanalClient implements InitializingBean, DisposableBean {
    private CanalConnector canalConnector;

    /**
     * canal host
     */
    @Value("${canal.host}")
    private String host;

    /**
     * canal port
     */
    @Value("${canal.port}")
    private String port;

    /**
     * canal destination
     */
    @Value("${canal.destination}")
    private String destination;

    /**
     * canal username
     */
    @Value("${canal.username}")
    private String username;

    /**
     * canal password
     */
    @Value("${canal.password}")
    private String password;

    @Value("${canal.subscribe}")
    private String subscribe;

    @Value("${canal.zk-server-list}")
    private String zkServerList;

    @Override
    public void destroy() {
        canalConnector.disconnect();
    }

    @Override
    public void afterPropertiesSet() {
        // 单机
        canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(host, Integer.valueOf(port)), destination, username, password);

        // 创建带cluster模式的客户端链接，自动完成failover切换
        // canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(new InetSocketAddress(host, Integer.valueOf(port))), destination, username, password);

        // 创建带cluster模式的客户端链接，自动完成failover切换，服务器列表自动扫描
        // canalConnector = CanalConnectors.newClusterConnector(zkServerList, destination, "", "");

        canalConnector.connect();
        canalConnector.subscribe(subscribe);
        // 回滚寻找上次中断的位置
        canalConnector.rollback();
        log.info("canal客户端启动成功");
    }

    @Bean
    public CanalConnector canalConnector() {
        return canalConnector;
    }
}
