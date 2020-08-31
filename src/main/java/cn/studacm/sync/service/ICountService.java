package cn.studacm.sync.service;

import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.model.request.CountRequest;

import java.util.List;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

public interface ICountService {

    /**
     * 统计
     *
     * @param request
     * @return
     */
    CountDTO count(CountRequest request);
}
