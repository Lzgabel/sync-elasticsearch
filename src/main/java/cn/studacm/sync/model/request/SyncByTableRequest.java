package cn.studacm.sync.model.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Data
public class SyncByTableRequest {

    private String database = "oj";

    @NotBlank(message = "table 不能为空")
    private String table;

    private Integer stepSize = 500;

    private Long from;

    private Long to;
}
