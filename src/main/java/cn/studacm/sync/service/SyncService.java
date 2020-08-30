package cn.studacm.sync.service;

import cn.studacm.sync.model.request.SyncByTableRequest;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
public interface SyncService {
    /**
     * 通过database和table同步数据库
     *
     * @param request 请求参数
     * @return 后台同步进程执行成功与否
     */
    boolean syncByTable(SyncByTableRequest request);
}
