package com.integration.config.Advice;

import com.integration.aop.log.service.IomCampActionApiService;
import com.integration.config.feign.CustomFeignException;
import com.integration.entity.PageResult;
import com.integration.utils.DataUtils;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一错误处理
 */
@ControllerAdvice
@ResponseBody
public class ErrorControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @Autowired
    private IomCampActionApiService campActionApiService;


    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public PageResult handleException(HttpServletRequest req, Exception e) {
        log.error("捕获错误：未定义", e);

        String token = req.getHeader("token");
        String userId = TokenUtils.getTokenUserId(token);
        String userDlzh = TokenUtils.getTokenDldm(token);
        String userName = null;
        if(userId.equals("-1")){
            userName = "system";
        } else {
            userName = TokenUtils.getTokenUserName(token);
        }
        //campActionApiService.saveAction(req.getRequestURI(), req.getParameterMap(), JSONObject.toJSONString(e), userId, userDlzh, userName);
        return DataUtils.returnPr(false,"系统错误，请联系管理员", null, 0,getExMsg(e, req));
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public PageResult handle12(HttpServletRequest req, Exception e) {
        log.error("捕获错误：空指针", e);
        return DataUtils.returnPr(false,"系统错误，请联系管理员", null, 0,getExMsg(e, req));
    }


    private String getExMsg(Exception e, HttpServletRequest req){
        String token = req.getHeader("token");
        String userId = TokenUtils.getTokenUserId(token);
        String userDlzh = TokenUtils.getTokenDldm(token);
        String userName = null;
        if(userId.equals("-1")){
            userName = "system";
        } else {
            userName = TokenUtils.getTokenUserName(token);
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        //String logId = campActionApiService.saveAction(req.getRequestURI(), req.getParameterMap(), sw.toString(), userId, userDlzh, userName);
        if(e.getCause() instanceof CustomFeignException){
            String errorMsg = ((CustomFeignException)e.getCause()).getExceptionMsg();
            log.error("feign调用异常：");
            log.error(errorMsg);
            return errorMsg;
        }

        return sw.toString();
    }
}