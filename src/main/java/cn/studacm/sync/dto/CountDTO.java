package cn.studacm.sync.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

@Data
@Accessors(chain = true)
public class CountDTO {

    /**
     * 代码行数
     */
    private Long codeLines;

    /**
     * user id
     */
    private Integer userId;

    /**
     * username
     */
    private String userName;

    /**
     * nickname
     */
    private String nickName;

    /**
     * email
     */
    private String email;

    /**
     * school
     */
    private String school;

    /**
     * sex
     */
    private String sex;

    /**
     * college
     */
    private String college;

    /**
     * major
     */
    private String major;

    /**
     * grade
     */
    private String grade;

    /**
     * class
     */
    private String clazz;

    /**
     * 时间
     */
    private String date;
}
