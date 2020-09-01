package cn.studacm.sync.service.impl;

import cn.studacm.sync.document.CodeDocument;
import cn.studacm.sync.entity.Code;
import cn.studacm.sync.entity.Solution;
import cn.studacm.sync.entity.User;
import cn.studacm.sync.service.IElasticsearchService;
import cn.studacm.sync.service.ISolutionService;
import cn.studacm.sync.service.IUserService;
import cn.studacm.sync.util.JsonUtil;
import io.searchbox.client.JestClient;
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
@Service("code-insert")
public class CodeInsertElasticsearchServiceImpl extends AbstractElasticsearchService {

    @Autowired
    IUserService userService;

    @Autowired
    ISolutionService solutionService;

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
        Bulk.Builder builder = new Bulk.Builder().defaultIndex(index).defaultType(type);
        for (Map<String, Object> data: dataList) {

            CodeDocument document = getDocument(data);
            if (Objects.nonNull(document)) {
                builder.addAction(new Index.Builder(document).index(index).type(type).build());
            }
        }
        try {
            jestClient.execute(builder.build());
        } catch (Exception e) {
            log.error("批量同步异常", e);
        }
    }

    private CodeDocument getDocument(Map<String, Object> data) {
        Code code = JsonUtil.fromJson(data, Code.class);

        Solution solution = solutionService.getById(code.getSolutionId());
        if (Objects.isNull(solution)) {
            return null;
        }
        User user = userService.getById(solution.getUserId());
        if (Objects.isNull(user)) {
            return null;
        }

        CodeDocument codeDocument = new CodeDocument();
        BeanUtils.copyProperties(code, codeDocument);
        BeanUtils.copyProperties(solution, codeDocument);
        BeanUtils.copyProperties(user, codeDocument);
        if (StringUtils.isNotBlank(code.getCodeContent())) {
            if (StringUtils.contains(code.getCodeContent(), "\r")) {
                codeDocument.setCodeLines(StringUtils.countMatches(code.getCodeContent(), "\r\n") + 1);
            } else {
                codeDocument.setCodeLines(StringUtils.countMatches(code.getCodeContent(), "\n") + 1);
            }
        }
        return codeDocument;
    }
}
