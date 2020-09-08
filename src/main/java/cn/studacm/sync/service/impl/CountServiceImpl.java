package cn.studacm.sync.service.impl;

import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.entity.User;
import cn.studacm.sync.model.request.CountRequest;
import cn.studacm.sync.service.ICountService;
import cn.studacm.sync.util.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.DateHistogramAggregation;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.SumAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * 索引名称
     */
    public static final String USER_INDEX_NAME = "user_index";

    /**
     * 索引类型
     */
    public static final String TYPE_NAME = "_doc";

    @Autowired
    JestClient jestClient;

    @Override
    public List<CountDTO> count(CountRequest request) {

        SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
        DateHistogramAggregationBuilder dateGroup = AggregationBuilders.dateHistogram("group")
                .field("subTime")
                .calendarInterval(DateHistogramInterval.MONTH)
                .format("MM月")
                .minDocCount(0);
        AggregationBuilder aggregationBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
        dateGroup.subAggregation(aggregationBuilder);
        String query = searchSourceBuilder.aggregation(dateGroup).toString();

        Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);

        List<CountDTO> res = Lists.newArrayList();
        try {
            SearchResult result = jestClient.execute(builder.build());
            MetricAggregation aggregations = result.getAggregations();
            List<DateHistogramAggregation.DateHistogram> group = aggregations.getDateHistogramAggregation("group").getBuckets();
            group.forEach(v -> {
                CountDTO countDTO = new CountDTO();
                String date = v.getTimeAsString();
                countDTO.setDate(date);
                SumAggregation codeLines = v.getSumAggregation("codeLines");
                countDTO.setCodeLines(codeLines.getSum().longValue());
                res.add(countDTO);
            });
        } catch (IOException e) {
            log.error("查询失败", e);
        }

        return res;
    }

    private SearchSourceBuilder buildSearchBuilder(CountRequest request) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolean range = false;
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("subTime");
        if (Objects.nonNull(request.getBegin())) {
            rangeQuery.gte(TimeUtil.dateToTimestamp(request.getBegin()));
            range = true;
        }

        if (Objects.nonNull(request.getEnd())) {
            rangeQuery.lte(TimeUtil.dateToTimestamp(request.getEnd()));
            range = true;
        }
        if (range) {
            boolQueryBuilder.must(rangeQuery);
        }

        if (StringUtils.isNotBlank(request.getUsername())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("userName", request.getUsername()));
        }

        if (Objects.nonNull(request.getResult())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("result", request.getResult()));
        }
        searchSourceBuilder.query(boolQueryBuilder);

        return searchSourceBuilder;
    }

    @Override
    public Map<Integer, CountDTO> groupByResult(CountRequest request) {
        SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);

        AggregationBuilder aggregationGroupByBuilder = AggregationBuilders.terms("group").field("result");
        AggregationBuilder aggregationSubBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
        aggregationGroupByBuilder.subAggregation(aggregationSubBuilder);
        String query = searchSourceBuilder.aggregation(aggregationGroupByBuilder).toString();

        Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);
        Map<Integer, CountDTO> res = Maps.newHashMap();
        try {
            SearchResult result = jestClient.execute(builder.build());
            MetricAggregation aggregations = result.getAggregations();
            TermsAggregation group = aggregations.getTermsAggregation("group");
            group.getBuckets().forEach(v -> {
                SumAggregation codeLines = v.getSumAggregation("codeLines");
                CountDTO countDTO = new CountDTO().setCodeLines(codeLines.getSum().longValue());
                res.put(Integer.valueOf(v.getKey()), countDTO);
            });
        } catch (IOException e) {
            log.error("查询失败", e);
        }
        return res;
    }

    @Override
    public List<CountDTO> ranklist(CountRequest request) {
        SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
        AggregationBuilder aggregationSubBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
        searchSourceBuilder.size(20);
        AggregationBuilder groupBy = AggregationBuilders.terms("group")
                .field("userId").subAggregation(aggregationSubBuilder)
                // 根据 code line 前20个
                .order(BucketOrder.aggregation("codeLines", false)).size(20);

        String query = searchSourceBuilder.aggregation(groupBy).toString();


        Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);
        List<CountDTO> res = Lists.newArrayList();
        try {
            SearchResult result = jestClient.execute(builder.build());
            MetricAggregation aggregations = result.getAggregations();
            TermsAggregation group = aggregations.getTermsAggregation("group");
            List<String> userIdList = group.getBuckets().stream().map(TermsAggregation.Entry::getKey).collect(Collectors.toList());

            List<SearchResult.Hit<User, Void>> hits = getUser(userIdList);

            group.getBuckets().forEach(v -> {
                String userId = v.getKey();
                SumAggregation codeLines = v.getSumAggregation("codeLines");
                Optional<User> first = hits.stream().map(i -> i.source).filter(i -> StringUtils.equals(i.getUserId().toString(), userId)).findFirst();
                if (first.isPresent()) {
                    CountDTO countDTO = new CountDTO();
                    BeanUtils.copyProperties(first.get(), countDTO);
                    countDTO.setCodeLines(codeLines.getSum().longValue());
                    res.add(countDTO);
                }
            });

        } catch (IOException e) {
            log.error("查询失败", e);
        }
        return res;
    }

    @Override
    public List<CountDTO> linelist(CountRequest request) {

        SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
        DateHistogramAggregationBuilder dateGroup = AggregationBuilders.dateHistogram("group")
                .field("subTime")
                .calendarInterval(DateHistogramInterval.MONTH)
                .format("MM月")
                .minDocCount(0);
        AggregationBuilder aggregationBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
        dateGroup.subAggregation(aggregationBuilder);
        String query = searchSourceBuilder.aggregation(dateGroup).toString();


        Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);
        List<CountDTO> res = Lists.newArrayList();
        try {
            SearchResult result = jestClient.execute(builder.build());
            MetricAggregation aggregations = result.getAggregations();
            TermsAggregation group = aggregations.getTermsAggregation("group");

            group.getBuckets().forEach(v -> {
                SumAggregation codeLines = v.getSumAggregation("codeLines");
                CountDTO countDTO = new CountDTO();
                countDTO.setDate(v.getKeyAsString());
                countDTO.setCodeLines(codeLines.getSum().longValue());
                res.add(countDTO);
            });

        } catch (IOException e) {
            log.error("查询失败", e);
        }
        return res;
    }

    @Override
    public List<CountDTO> quarter(CountRequest request) {
            SearchSourceBuilder searchSourceBuilder = buildSearchBuilder(request);
            DateHistogramAggregationBuilder dateGroup = AggregationBuilders.dateHistogram("group")
                    .field("subTime")
                    .calendarInterval(DateHistogramInterval.QUARTER)
                    .format("'Q'q")
                    .minDocCount(0);
            AggregationBuilder aggregationBuilder = AggregationBuilders.sum("codeLines").field("codeLines");
            dateGroup.subAggregation(aggregationBuilder);
            String query = searchSourceBuilder.aggregation(dateGroup).toString();


            Search.Builder builder = new Search.Builder(query).addIndex(INDEX_NAME).addType(TYPE_NAME);
            List<CountDTO> res = Lists.newArrayList();
            try {
                SearchResult result = jestClient.execute(builder.build());
                MetricAggregation aggregations = result.getAggregations();
                TermsAggregation group = aggregations.getTermsAggregation("group");

                group.getBuckets().forEach(v -> {
                    SumAggregation codeLines = v.getSumAggregation("codeLines");
                    CountDTO countDTO = new CountDTO();
                    countDTO.setDate(v.getKeyAsString());
                    countDTO.setCodeLines(codeLines.getSum().longValue());
                    res.add(countDTO);
                });

            } catch (IOException e) {
                log.error("查询失败", e);
            }
            return res;
    }

    private List<SearchResult.Hit<User, Void>> getUser(List<String> userIdList) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder().filter(QueryBuilders.termsQuery("userId", userIdList));
        searchSourceBuilder.query(boolQueryBuilder);
        // 查询用户信息
        Search.Builder builder = new Search.Builder(searchSourceBuilder.toString()).addIndex(USER_INDEX_NAME).addType(TYPE_NAME);
        SearchResult execute = null;
        try {
            execute = jestClient.execute(builder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return execute.getHits(User.class);
    }

    public static void main(String[] args) throws ParseException {
        DateTimeFormatter QUARTER_FORMAT = DateTimeFormatter.ofPattern("'Q'q");
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(DATE_FORMAT.parse("2007-01-23").toInstant().atZone(ZoneId.systemDefault()).format(QUARTER_FORMAT));
    }
}
