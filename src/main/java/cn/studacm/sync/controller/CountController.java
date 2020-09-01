package cn.studacm.sync.controller;

import cn.studacm.sync.constants.ResultEnum;
import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.model.request.CountRequest;
import cn.studacm.sync.model.response.Response;
import cn.studacm.sync.service.ICountService;
import cn.studacm.sync.util.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020/8/31
 * @since 1.0.0
 */

@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    ICountService countService;

    private static final LocalDate BEGIN_DATE = TimeUtil.parseLocalDate("2006-01-01 00:00:00");

    @GetMapping("/self")
    public Response<CountDTO> countBySelf(@RequestBody @Validated CountRequest request) {
        return Response.success(countService.count(request));
    }

    @GetMapping
    public ModelAndView index(ModelMap model, ModelAndView view) {

        LocalDate now = LocalDate.now();
        long diff = TimeUtil.diffYears(BEGIN_DATE, now);

        CountRequest filterResultRequest = new CountRequest();
        LocalDateTime firstDayOfYear = TimeUtil.getFirstDayOfYear(BEGIN_DATE);
        LocalDateTime lastDayOfYear = TimeUtil.getLastDayOfYear(LocalDate.now());
        filterResultRequest.setBegin(TimeUtil.toDate(firstDayOfYear));
        filterResultRequest.setEnd(TimeUtil.toDate(lastDayOfYear));
        // All 统计
        CountDTO countDTO = countService.count(filterResultRequest);
        Long all = countDTO.getCodeLines();
        model.addAttribute("all", all);

        // 年平均
        model.addAttribute("avg", "年平均");
        model.addAttribute("avg", countDTO.getCodeLines()/diff);

        // AC 统计
        List<Map<String, Object>> pieList = Lists.newArrayList();
        filterResultRequest.setResult(ResultEnum.Accepted.value());
        countDTO = countService.count(filterResultRequest);
        Long ac = countDTO.getCodeLines();
        model.addAttribute("ac", ac);
        Map<String, Object> acpie = Maps.newHashMap();
        acpie.put("value", ac);
        acpie.put("name", "AC");
        pieList.add(acpie);



        // WA 统计
        filterResultRequest.setResult(ResultEnum.WrongAnswer.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> wapie = Maps.newHashMap();
        wapie.put("value", countDTO.getCodeLines());
        wapie.put("name", "WA");
        pieList.add(wapie);

        // TLE 统计
        filterResultRequest.setResult(ResultEnum.TimeLimitExceeded.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> tlepie = Maps.newHashMap();
        tlepie.put("value", countDTO.getCodeLines());
        tlepie.put("name", "TLE");
        pieList.add(tlepie);

        // OL 统计
        filterResultRequest.setResult(ResultEnum.OutputLimit.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> olpie = Maps.newHashMap();
        olpie.put("value", countDTO.getCodeLines());
        olpie.put("name", "OL");
        pieList.add(olpie);

        // RE 统计
        filterResultRequest.setResult(ResultEnum.RuntimeError.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> repie = Maps.newHashMap();
        repie.put("value", countDTO.getCodeLines());
        repie.put("name", "RE");
        pieList.add(repie);

        // PE 统计
        filterResultRequest.setResult(ResultEnum.PresentationError.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> pepie = Maps.newHashMap();
        pepie.put("value", countDTO.getCodeLines());
        pepie.put("name", "PE");
        pieList.add(pepie);

        // CE 统计
        filterResultRequest.setResult(ResultEnum.CompileError.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> cepie = Maps.newHashMap();
        cepie.put("value", countDTO.getCodeLines());
        cepie.put("name", "CE");
        pieList.add(cepie);

        // SE 统计
        filterResultRequest.setResult(ResultEnum.SystemError.value());
        countDTO = countService.count(filterResultRequest);
        Map<String, Object> sepie = Maps.newHashMap();
        sepie.put("value", countDTO.getCodeLines());
        sepie.put("name", "SE");
        pieList.add(sepie);

        model.addAttribute("pies", pieList);

        // Other 统计
        model.addAttribute("other", all-ac);


        // 年提交量统计 -- begin

        List<CountDTO> res = Lists.newArrayList();
        for (int i = 0; i <= diff; i++) {
            CountRequest request = new CountRequest();
            LocalDate date = TimeUtil.plusYears(TimeUtil.toDate(BEGIN_DATE), i);
            firstDayOfYear = TimeUtil.getFirstDayOfYear(date);
            lastDayOfYear = TimeUtil.getLastDayOfYear(date);
            request.setBegin(TimeUtil.toDate(firstDayOfYear));
            request.setEnd(TimeUtil.toDate(lastDayOfYear));
            res.add(countService.count(request));
        }

        List<String> dataAxis = res.stream()
                .map(CountDTO::getBegin)
                .map(TimeUtil::toLocalDate)
                .map(TimeUtil::getYear)
                .map(String::valueOf)
                .collect(Collectors.toList());

        List<String> data = res.stream()
                .map(CountDTO::getCodeLines)
                //.map(i -> i/10000)
                .map(String::valueOf)
                .collect(Collectors.toList());

        model.addAttribute("dataAxis", dataAxis.toArray());
        model.addAttribute("data", data.toArray());
        // 年提交量统计 -- end
        view.setViewName("index");
        return view;
    }
}
