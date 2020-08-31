package cn.studacm.sync.service.impl;

import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.model.request.CountRequest;
import cn.studacm.sync.service.ICountService;
import cn.studacm.sync.util.TimeUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.SumAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

@Slf4j
@Service
public class CountServiceImpl implements ICountService {

    /**
     * 索引名称
     */
    public static final String INDEX_NAME = "code_index";

    /**
     * 索引类型
     */
    public static final String TYPE_NAME = "_doc";

    @Autowired
    JestClient jestClient;

    @Override
    public CountDTO count(CountRequest request) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("subTime")
                        .gte(TimeUtil.dateToTimestamp(request.getBegin()))
                        .lte(TimeUtil.dateToTimestamp(request.getEnd())));

        if (StringUtils.isNotBlank(request.getUserId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("userId", request.getUserId()));
        }

        searchSourceBuilder.query(boolQueryBuilder);
        AggregationBuilder aggregationBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
        String query = searchSourceBuilder.aggregation(aggregationBuilder).toString();

        Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);

        CountDTO countDTO = new CountDTO();
        BeanUtils.copyProperties(request, countDTO);
        try {
            SearchResult result = jestClient.execute(builder.build());
            SumAggregation sumAggregation = result.getAggregations().getSumAggregation("codeLines");
            Double sum = sumAggregation.getSum();
            countDTO.setCodeLines(sum.longValue());
        } catch (IOException e) {
            log.error("查询失败", e);
        }

        return countDTO;
    }
}
