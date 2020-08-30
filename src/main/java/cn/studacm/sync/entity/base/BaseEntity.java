package cn.studacm.sync.entity.base;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  公共实体
 * <p>
 *   @author lixin
 *   @date 2018/3/28 下午2:50
 *   @version 1.0
 */
@Data

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity extends Model<BaseEntity> implements Serializable {

    private static final long serialVersionUID = 3604321486155854026L;

    /**
     * 是否删除
     * <p>
     */
    @TableField(value = "deleted",fill = FieldFill.INSERT)
    protected Integer deleted;

    /**
     * 为额外的信息预留的字段
     */
    @TableField(fill = FieldFill.INSERT,exist = false)
    private String feature;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    protected Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE, update = "now()")
    protected Date gmtModified;

    /**
     *
     */
    @TableField(exist = false)
    protected Map<String, Object> criteria;


    public Map<String, Object> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, Object> criteria) {
        this.criteria = criteria;
    }

    /**
     * 设置参数条件
     *
     * @param key   参数名称
     * @param value 参数值
     * @param <T>   参数对象
     * @return
     */
    public <T extends BaseEntity> T addCriteria(String key, Object value) {
        criteria = criteria == null ? new HashMap<>(16) : criteria;
        criteria.put(key, value);
        return (T) this;
    }
}
