package cn.studacm.sync.service.impl;

import cn.studacm.sync.entity.Solution;
import cn.studacm.sync.dao.SolutionMapper;
import cn.studacm.sync.service.ISolutionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto create
 * @since 2020-08-30
 */
@Service
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements ISolutionService {

    @Override
    public Solution getById(Integer id) {
        LambdaQueryWrapper<Solution> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Solution::getSolutionId, id);
        return this.getOne(wrapper);
    }
}
