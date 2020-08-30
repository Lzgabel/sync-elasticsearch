package cn.studacm.sync.entity;

import cn.studacm.sync.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @TableId(value = "solution_id", type = IdType.AUTO)
    private Integer solutionId;

    private Integer problemId;

    private Integer userId;

    private Integer contestId;

    private Integer takeTime;

    private Integer takeMemory;

    private String proLang;

    private Integer codeLength;

    private Date subTime;

    private Integer result;

}
