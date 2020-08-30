package cn.studacm.sync.entity;

import cn.studacm.sync.entity.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("user")
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String nickName;

    private String email;

    private String school;

    private String sex;

    private String college;

    private String major;

    private String grade;

    @TableField("class")
    private String clazz;
}
