package com.integration.config;

import com.integration.entity.PageResult;
import com.integration.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Error
 * @Description zuul异常处理类
 * @Author zhangfeng
 * @Date 2020/7/13 17:30
 * @Version 1.0
 **/
@RestController
public class ErrorControllerImpl implements ErrorController {

    Logger log = LoggerFactory.getLogger(ErrorControllerImpl.class);

    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping({"${server.error.path:/error}"})
    public PageResult error(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        log.error("捕捉异常：", exception);
        Map<String, Object> body = new HashMap<>();
        HttpStatus status = HttpStatus.valueOf(code);
//        return new ResponseEntity(body, status);
        return DataUtils.returnPr(false,"系统错误，请联系管理员", null, 0,"");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
