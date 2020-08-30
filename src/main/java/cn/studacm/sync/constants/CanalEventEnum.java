package cn.studacm.sync.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * 〈功能简述〉<br>
 * 〈canal 事件激活类型枚举〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

public enum  CanalEventEnum {

    /**
     * 插入事件
     */
    INSERT("insert"),

    /**
     * 更新事件
     */
    UPDATE("update"),

    /**
     * 删除事件
     */
    DELETE("delete");

    private String value;

    CanalEventEnum(String value) {
        this.value = value;
    }

    public boolean isEqual(CanalEventEnum event) {
        return StringUtils.equals(this.value, event.value);
    }

    public String value() {
        return this.value;
    }
}
