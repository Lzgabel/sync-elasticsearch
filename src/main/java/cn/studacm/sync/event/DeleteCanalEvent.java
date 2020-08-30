package cn.studacm.sync.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
public class DeleteCanalEvent extends AbstractCanalEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DeleteCanalEvent(Entry source) {
        super(source);
    }
}
