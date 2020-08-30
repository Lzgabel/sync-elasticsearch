package cn.studacm.sync.configuration;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈mybatis map 驼峰命名转换〉<br>
 *      mybatis-config 中添加
 *      <setting name="mapUnderscoreToCamelCase" value="true"/>
 *      <objectWrapperFactory type="MapObjectWrapperFactory"/>
 *
 * @author lizhi
 * @date 2020-08-20
 * @since 1.0.0
 */
public class MapObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object o) {
        return new CustomWrapper(metaObject, (Map) o);
    }
}
