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
    List<CountDTO> count(CountRequest request);


    /**
     * 根据评测结果进行分组
     * @param request
     * @return
     */
    Map<Integer, CountDTO> groupByResult(CountRequest request);

    /**
     * 排行榜
     * @param request
     * @return
     */
    List<CountDTO> ranklist(CountRequest request);


    /**
     * 近6个月提交量折线图统计
     * @param request
     * @return
     */
    List<CountDTO> linelist(CountRequest request);
}
