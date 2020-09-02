package cn.studacm.sync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.searchbox.annotations.JestId;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author auto create
 * @since 2020-08-30
 */
@Data
@TableName("solution")
public class Solution {

    private static final long serialVersionUID = 1L;

    @JestId
    @TableId(value = "solution_id", type = IdType.AUTO)
    private Integer solutionId;

    private Integer problemId;

    private Integer userId;

    private Integer contestId;

    private Integer takeTime;

    private Integer takeMemory;

    private String proLang;

    private Integer codeLength;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date subTime;

    private Integer result;

}
