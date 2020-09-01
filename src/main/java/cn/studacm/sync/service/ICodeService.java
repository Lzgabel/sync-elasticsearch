package cn.studacm.sync.service;

import cn.studacm.sync.entity.Code;
import cn.studacm.sync.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto create
 * @since 2020-08-30
 */
public interface ICodeService extends IService<Code> {

    Code getBySolutionId(Integer id);
}
