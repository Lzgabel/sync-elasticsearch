package cn.studacm.sync.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
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
public class CountRequest {

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不可为空")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date begin;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不可为空")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date end;

    /**
     * user id
     */
    private String userId;

    /**
     * result
     */
    private Integer result;
}
