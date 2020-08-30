package cn.studacm.sync.controller.advice;

import cn.studacm.sync.model.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */

@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    @ResponseBody
    public Object exceptionHandler(Exception e, HttpServletResponse response) {
        log.error("unknown_error", e);
        return new Response<>(2, e.getMessage(), null).toString();
    }
}
