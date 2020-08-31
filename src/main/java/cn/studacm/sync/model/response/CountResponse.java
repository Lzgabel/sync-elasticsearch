package cn.studacm.sync.model.response;

import lombok.Data;

import java.util.Date;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

@Data
public class CountResponse {

    /**
     * 代码行数
     */
    private Long codeLines;

    /**
     * 用户 id
     */
    private String userId;

    /**
     * 开始时间
     */
    private Date begin;

    /**
     * 结束时间
     */
    private Date end;
}
