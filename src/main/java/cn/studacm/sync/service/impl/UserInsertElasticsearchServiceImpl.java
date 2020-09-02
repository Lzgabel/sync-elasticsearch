package cn.studacm.sync.service.impl;

import cn.studacm.sync.document.CodeDocument;
import cn.studacm.sync.entity.Code;
import cn.studacm.sync.entity.Solution;
import cn.studacm.sync.entity.User;
import cn.studacm.sync.service.ISolutionService;
import cn.studacm.sync.service.IUserService;
import cn.studacm.sync.util.JsonUtil;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 〈功能简述〉<br>
 * 〈用户增量同步〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Slf4j
@Service("user-insert")
public class UserInsertElasticsearchServiceImpl extends AbstractElasticsearchService {

    @Autowired
    IUserService userService;

    @Autowired
    ISolutionService solutionService;

    @Override
    public void insertById(String index, String type, String id, Map<String, Object> dataMap) {

        User user = JsonUtil.fromJson(dataMap, User.class);
        if (Objects.nonNull(user)) {
            Index.Builder builder = new Index.Builder(user);
            Index create = builder.index(index).type(type).build();
            try {
                jestClient.execute(create);
            } catch (IOException e) {
                log.error("同步es失败", e);
            }
        }
    }

    @Override
    public void batchInsertById(String index, String type, List<Map<String, Object>> dataList) {
        Bulk.Builder builder = new Bulk.Builder().defaultIndex(index).defaultType(type);
        for (Map<String, Object> data: dataList) {

            User user = JsonUtil.fromJson(data, User.class);
            if (Objects.nonNull(user)) {
                builder.addAction(new Index.Builder(user).index(index).type(type).build());
            }
        }
        try {
            jestClient.execute(builder.build());
        } catch (Exception e) {
            log.error("批量同步异常", e);
        }
    }
}
