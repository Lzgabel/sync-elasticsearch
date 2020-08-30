package cn.studacm.sync.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
public abstract class AbstractCanalEvent extends ApplicationEvent implements Serializable {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AbstractCanalEvent(Entry source) {
        super(source);
    }

    public Entry getEntry() {
        return (Entry) source;
    }
}
