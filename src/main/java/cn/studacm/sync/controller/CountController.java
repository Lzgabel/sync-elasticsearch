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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/year")
    public Response<List<CountResponse>> count() {

        LocalDate now = LocalDate.now();
        long diff = TimeUtil.diffYears(BEGIN_DATE, now);

        List<CountResponse> res = Lists.newArrayList();
        for (int i = 0; i <= diff; i++) {
            CountRequest request = new CountRequest();
            LocalDate date = TimeUtil.plusYears(TimeUtil.toDate(BEGIN_DATE), i);
            LocalDateTime firstDayOfYear = TimeUtil.getFirstDayOfYear(date);
            LocalDateTime lastDayOfYear = TimeUtil.getLastDayOfYear(date);
            request.setBegin(TimeUtil.toDate(firstDayOfYear));
            request.setEnd(TimeUtil.toDate(lastDayOfYear));
            CountDTO countDTO = countService.count(request);
            CountResponse response = new CountResponse();
            BeanUtils.copyProperties(countDTO, response);
            res.add(response);
        }
        return Response.success(res);
    }

    @GetMapping("/self")
    public Response<CountDTO> countBySelf(@RequestBody @Validated CountRequest request) {
        return Response.success(countService.count(request));
    }
}
