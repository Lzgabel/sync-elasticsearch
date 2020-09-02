package cn.studacm.sync.constants;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author lzgabel
 * @date 2020/09/01 18:41
 */

public enum ResultEnum {

    /**
     * 等待中
     */
    Waiting(0, "Waiting"),

    /**
     * ac
     */
    Accepted(1, "Accepted"),

    /**
     * tle
     */
    TimeLimitExceeded(2, "Time Limit Exceeded"),

    /**
     * wa
     */
    WrongAnswer(4, "Wrong Answer"),

    /**
     * re
     */
    RuntimeError(5, "Runtime Error"),

    /**
     * ol
     */
    OutputLimit(6, "Output Limit"),

    /**
     * ce
     */
    CompileError(7, "Compile Error"),

    /**
     * pe
     */
    PresentationError(8, "Presentation Error"),

    /**
     * se
     */
    SystemError(11, "System Error"),

    /**
     * judging
     */
    Judging(12, "Judging");


    private String desc;
    private Integer value;

    ResultEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public boolean isEqual(Integer value) {
        return this.value.equals(value);
    }

    public Integer value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    static List<ResultEnum> resultEnumList = Lists.newArrayList();

    static {
        resultEnumList.add(Accepted);
        resultEnumList.add(TimeLimitExceeded);
        resultEnumList.add(WrongAnswer);
        resultEnumList.add(RuntimeError);
        resultEnumList.add(OutputLimit);
        resultEnumList.add(CompileError);
        resultEnumList.add(PresentationError);
        resultEnumList.add(SystemError);
    }

    public String trim() {
        String res = StringUtils.trimToEmpty(desc);
        return res.replaceAll(" ", "");
    }
}
