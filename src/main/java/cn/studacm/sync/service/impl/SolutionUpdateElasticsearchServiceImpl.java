package cn.studacm.sync.service.impl;

import cn.studacm.sync.document.CodeDocument;
import cn.studacm.sync.entity.Code;
import cn.studacm.sync.entity.Solution;
import cn.studacm.sync.entity.User;
import cn.studacm.sync.service.ICodeService;
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
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Slf4j
@Service("solution-update")
public class SolutionUpdateElasticsearchServiceImpl extends AbstractElasticsearchService {

    @Autowired
    IUserService userService;

    @Autowired
    ISolutionService solutionService;

    @Autowired
    ICodeService codeService;

    @Override
    public void insertById(String index, String type, String id, Map<String, Object> dataMap) {

        CodeDocument document = getDocument(dataMap);
        if (Objects.nonNull(document)) {
            Index.Builder builder = new Index.Builder(document);
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
        // do nothing
    }

    private CodeDocument getDocument(Map<String, Object> data) {
        Object subTime = data.get("subTime");
        Solution solution = JsonUtil.fromJson(data, Solution.class);

        Code code = codeService.getBySolutionId(solution.getSolutionId());
        if (Objects.isNull(code)) {
            return null;
        }

        User user = userService.getById(solution.getUserId());
        if (Objects.isNull(user)) {
            return null;
        }

        CodeDocument codeDocument = new CodeDocument();
        BeanUtils.copyProperties(code, codeDocument);
        BeanUtils.copyProperties(solution, codeDocument);

        return codeDocument;
    }
}
