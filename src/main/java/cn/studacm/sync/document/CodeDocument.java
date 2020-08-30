package cn.studacm.sync.document;

import io.searchbox.annotations.JestId;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Data
public class CodeDocument implements Serializable {

    /**
     * code id
     */
    @JestId
    private Integer codeId;

    /**
     * solution id
     */
    private Integer solutionId;

    /**
     * code content
     */
    private String codeContent;

    /**
     * code lines
     */
    private Integer codeLines;

    /**
     * problem id
     */
    private Integer problemId;

    /**
     * contest id
     */
    private Integer contestId;

    /**
     * take time
     */
    private Integer takeTime;

    /**
     * take memory
     */
    private Integer takeMemory;

    /**
     * pro language
     */
    private String proLang;

    /**
     * code length
     */
    private Integer codeLength;

    /**
     * submit time
     */
    private Date subTime;

    /**
     * retult
     */
    private Integer result;

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

}
