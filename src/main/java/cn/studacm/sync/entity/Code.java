package cn.studacm.sync.entity;

import cn.studacm.sync.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author auto create
 * @since 2020-08-30
 */
@Data
@TableName("code")
public class Code  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "code_id", type = IdType.AUTO)
    private Integer codeId;

    private Integer solutionId;

    private String codeContent;


}
