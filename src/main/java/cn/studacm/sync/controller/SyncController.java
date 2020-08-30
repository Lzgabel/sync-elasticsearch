package cn.studacm.sync.controller;

import cn.studacm.sync.model.request.SyncByTableRequest;
import cn.studacm.sync.model.response.Response;
import cn.studacm.sync.service.SyncService;
import cn.studacm.sync.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sync")
public class SyncController {
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Resource
    private SyncService syncService;

    /**
     * 通过库名和表名全量同步数据
     *
     * @param request 请求参数
     */
    @PostMapping("/byTable")
    public String syncTable(@Validated @RequestBody SyncByTableRequest request) {
        logger.debug("request_info: " + JsonUtil.toJson(request));
        String response = Response.success(syncService.syncByTable(request)).toString();
        logger.debug("response_info: " + JsonUtil.toJson(request));
        return response;
    }
}
