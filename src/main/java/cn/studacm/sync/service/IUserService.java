package cn.studacm.sync.service;

import cn.studacm.sync.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto create
 * @since 2020-08-30
 */
public interface IUserService extends IService<User> {

    User getById(Integer id);
}
