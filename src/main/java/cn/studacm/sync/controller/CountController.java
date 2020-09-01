package cn.studacm.sync.controller;

import cn.studacm.sync.dto.CountDTO;
import cn.studacm.sync.model.request.CountRequest;
import cn.studacm.sync.model.response.CountResponse;
import cn.studacm.sync.model.response.Response;
import cn.studacm.sync.service.ICountService;
import cn.studacm.sync.util.TimeUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
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

        List<CountDTO> res = Lists.newArrayList();
        for (int i = 0; i <= diff; i++) {
            CountRequest request = new CountRequest();
            LocalDate date = TimeUtil.plusYears(TimeUtil.toDate(BEGIN_DATE), i);
            LocalDateTime firstDayOfYear = TimeUtil.getFirstDayOfYear(date);
            LocalDateTime lastDayOfYear = TimeUtil.getLastDayOfYear(date);
            request.setBegin(TimeUtil.toDate(firstDayOfYear));
            request.setEnd(TimeUtil.toDate(lastDayOfYear));
            CountDTO countDTO = countService.count(request);
            res.add(countDTO);
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
        view.setViewName("index");
        return view;
    }
}
