package cn.studacm.sync.controller;

import cn.studacm.sync.constants.ResultEnum;
import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.model.request.CountRequest;
import cn.studacm.sync.model.response.Response;
import cn.studacm.sync.service.ICountService;
import cn.studacm.sync.util.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.searchbox.strings.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

@RestController
@RequestMapping("/")
public class CountController {

    @Autowired
    ICountService countService;

    private static final LocalDate BEGIN_DATE = TimeUtil.parseLocalDate("2009-01-01 00:00:00");

    @GetMapping("/self")
    public Response<CountDTO> countBySelf(@RequestBody @Validated CountRequest request) {
        //return Response.success(countService.count(request));
        return null;
    }

    @GetMapping(value = {"", "/self/{username}"})
    public ModelAndView index(ModelMap model, ModelAndView view,
                              @PathVariable(value = "username", required = false) String userName) {
        // 年提交量统计 -- begin
        CountRequest param = new CountRequest();
        param.setUsername(userName);
        param.setBegin(TimeUtil.toDate(BEGIN_DATE));
        List<CountDTO> res = countService.yearCount(param);

        List<String> dataAxis = res.stream()
                .map(CountDTO::getDate)
                .collect(Collectors.toList());

        List<String> data = res.stream()
                .map(CountDTO::getCodeLines)
                .map(i -> {
                    if (StringUtils.isNotBlank(userName)) {
                        return i;
                    } else {
                        return i/10000;
                    }
                })
                .map(String::valueOf)
                .collect(Collectors.toList());

        List<Integer> step = IntStream.range(0, data.size()).filter(x -> x % 5 == 0).boxed().collect(Collectors.toList());

        if (StringUtils.isBlank(userName)) {
            model.addAttribute("unit", "万行");
        } else {
            model.addAttribute("unit", "行");
        }
        model.addAttribute("dataAxis", dataAxis.toArray());
        model.addAttribute("step", step);
        model.addAttribute("data", data.toArray());
        // 年提交量统计 -- end
        view.setViewName("index");
        return view;
    }

    @GetMapping(value = {"/year/{year}/{username}", "/year/{year}"})
    public ModelAndView detail(ModelMap model,
                               ModelAndView view,
                               @PathVariable(value = "year", required = false) String year,
                               @PathVariable(value = "username", required = false) String userName) {

        LocalDateTime localDateTime = LocalDateTime.now();
        if (StringUtils.isNotBlank(year)) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .toFormatter();
            localDateTime = LocalDateTime.parse(year, formatter);
        }

        Date date = TimeUtil.toDate(localDateTime);

        // 饼图统计
        pieCount(date, model, userName);

        // 年提交量柱状图统计
        barCount(date, model, userName);

        // 排行榜统计
        rankCount(date, model, userName);

        // 近6个月折线图统计
        lineCount(date, model, userName);

        // 季度统计
        quarterCount(date, model, userName);

        view.setViewName("detail");
        return view;
    }

    private void quarterCount(Date date, ModelMap model, String userName) {
        CountRequest request = new CountRequest();
        request.setEnd(TimeUtil.toDate(TimeUtil.getLastDayOfYear(date)));
        request.setBegin(TimeUtil.toDate(TimeUtil.getFirstDayOfYear(date)));
        request.setUsername(userName);
        List<CountDTO> quarter = countService.quarter(request);
        Long sum = quarter.stream().map(CountDTO::getCodeLines).mapToLong(Long::longValue).sum();

        model.addAttribute("quarter", quarter);
        model.addAttribute("sum", sum);
    }

    private void lineCount(Date date, ModelMap model, String userName) {
        CountRequest request = new CountRequest();
        request.setEnd(TimeUtil.toDate(TimeUtil.getLastDayOfYear(date)));
        request.setBegin(TimeUtil.toDate(TimeUtil.getFirstDayOfYear(date)));
        request.setUsername(userName);
        List<CountDTO> lineList = countService.linelist(request);

        List<String> dateList = lineList.stream().map(CountDTO::getDate).collect(Collectors.toList());
        List<Long> lineData = lineList.stream().map(CountDTO::getCodeLines).collect(Collectors.toList());

        model.addAttribute("dateList", dateList);
        model.addAttribute("lineData", lineData);
    }

    private void rankCount(Date date, ModelMap model, String userName) {
        CountRequest request = new CountRequest();
        request.setUsername(userName);
        request.setEnd(TimeUtil.toDate(TimeUtil.getLastDayOfYear(date)));
        request.setBegin(TimeUtil.toDate(TimeUtil.getFirstDayOfYear(date)));
        List<CountDTO> ranklist = countService.ranklist(request);

        model.addAttribute("ranklist", ranklist);
    }

    private void barCount(Date date, ModelMap model, String userName) {
        LocalDate now = LocalDate.now();

        long diff = TimeUtil.diffYears(BEGIN_DATE, now);

        // 年提交量统计 -- begin
        CountRequest param = new CountRequest();
        param.setUsername(userName);
        param.setEnd(TimeUtil.toDate(TimeUtil.getLastDayOfYear(date)));
        param.setBegin(TimeUtil.toDate(TimeUtil.getFirstDayOfYear(date)));
        List<CountDTO> res = countService.monthCount(param);

        List<String> dataAxis = res.stream()
                .map(CountDTO::getDate)
                .collect(Collectors.toList());

        List<String> data = res.stream()
                .map(CountDTO::getCodeLines)
                //.map(i -> i/10000)
                .map(String::valueOf)
                .collect(Collectors.toList());

        model.addAttribute("dataAxis", dataAxis.toArray());
        model.addAttribute("data", data.toArray());
        // 年提交量统计 -- end
    }

    private void pieCount(Date date, ModelMap model, String userName) {

        LocalDate now = LocalDate.now();
        long diff = TimeUtil.diffYears(BEGIN_DATE, now);

        CountRequest filterResultRequest = new CountRequest();
        filterResultRequest.setUsername(userName);
        filterResultRequest.setBegin(TimeUtil.toDate(TimeUtil.getFirstDayOfYear(date)));
        filterResultRequest.setEnd(TimeUtil.toDate(TimeUtil.getLastDayOfYear(date)));

        // 根据结果分组
        Map<Integer, CountDTO> group = countService.groupByResult(filterResultRequest);


        // All 统计
        long all = group.values().stream().mapToLong(CountDTO::getCodeLines).sum();
        model.addAttribute("all", all);

        // 年平均
        model.addAttribute("avg", "年平均");
        model.addAttribute("avg", all / diff);

        // 饼图列表 -- begin
        List<Map<String, Object>> pieList = Lists.newArrayList();

        // AC 统计
        CountDTO countDTO = group.get(ResultEnum.Accepted.value());
        Long ac = 0L;
        if (Objects.nonNull(countDTO)) {
            ac = countDTO.getCodeLines();
        }
        model.addAttribute("ac", ac);
        Map<String, Object> acpie = Maps.newHashMap();
        acpie.put("value", ac);
        acpie.put("name", "AC");
        pieList.add(acpie);


        // WA 统计
        countDTO = group.get(ResultEnum.WrongAnswer.value());
        Long wa = 0L;
        if (Objects.nonNull(countDTO)) {
            wa = countDTO.getCodeLines();
        }
        Map<String, Object> wapie = Maps.newHashMap();
        wapie.put("value", wa);
        wapie.put("name", "WA");
        pieList.add(wapie);

        // TLE 统计
        countDTO = group.get(ResultEnum.TimeLimitExceeded.value());
        Long tle = 0L;
        if (Objects.nonNull(countDTO)) {
            tle = countDTO.getCodeLines();
        }
        Map<String, Object> tlepie = Maps.newHashMap();
        tlepie.put("value", tle);
        tlepie.put("name", "TLE");
        pieList.add(tlepie);

        // OL 统计
        countDTO = group.get(ResultEnum.OutputLimit.value());
        Long ol = 0L;
        if (Objects.nonNull(countDTO)) {
            ol = countDTO.getCodeLines();
        }
        Map<String, Object> olpie = Maps.newHashMap();
        olpie.put("value", ol);
        olpie.put("name", "OL");
        pieList.add(olpie);

        // RE 统计
        countDTO = group.get(ResultEnum.RuntimeError.value());
        Long re = 0L;
        if (Objects.nonNull(countDTO)) {
            re = countDTO.getCodeLines();
        }
        Map<String, Object> repie = Maps.newHashMap();
        repie.put("value", re);
        repie.put("name", "RE");
        pieList.add(repie);

        // PE 统计
        countDTO = group.get(ResultEnum.PresentationError.value());
        Long pe = 0L;
        if (Objects.nonNull(countDTO)) {
            pe = countDTO.getCodeLines();
        }
        Map<String, Object> pepie = Maps.newHashMap();
        pepie.put("value", pe);
        pepie.put("name", "PE");
        pieList.add(pepie);

        // CE 统计
        countDTO = group.get(ResultEnum.CompileError.value());
        Long ce = 0L;
        if (Objects.nonNull(countDTO)) {
            ce = countDTO.getCodeLines();
        }
        Map<String, Object> cepie = Maps.newHashMap();
        cepie.put("value", ce);
        cepie.put("name", "CE");
        pieList.add(cepie);

        // SE 统计
        countDTO = group.get(ResultEnum.SystemError.value());
        Long se = 0L;
        if (Objects.nonNull(countDTO)) {
            se = countDTO.getCodeLines();
        }
        Map<String, Object> sepie = Maps.newHashMap();
        sepie.put("value", se);
        sepie.put("name", "SE");
        pieList.add(sepie);

        model.addAttribute("pies", pieList);

        // Other 统计
        model.addAttribute("other", all - ac);
        // 饼图 -- end
    }
}
