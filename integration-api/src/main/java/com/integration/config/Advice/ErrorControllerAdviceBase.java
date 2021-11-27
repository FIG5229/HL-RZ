package com.integration.config.Advice;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.integration.entity.PageResult;
import com.integration.utils.DataUtils;
import feign.codec.DecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 统一错误处理
 *
 * @author zf
 */
public class ErrorControllerAdviceBase implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ErrorControllerAdviceBase.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public PageResult handle(Exception e) {
        logger.error(e.getMessage());
        if (e instanceof MsgException) {
            MsgException msgException = (com.integration.config.Advice.MsgException) e;
            return DataUtils.returnPr(false, msgException.getMessage(), null, 0, msgException.getExceptionMsg());
        }
        //这里是feign的解码器中的错误，会包一层，不明白为啥
        if (e instanceof feign.codec.DecodeException) {
            DecodeException decodeException = (DecodeException) e;
            MsgException msgException = (com.integration.config.Advice.MsgException) decodeException.getCause();
            return DataUtils.returnPr(false, msgException.getMessage(), null, 0, msgException.getExceptionMsg());
        }
        return DataUtils.returnPr(false, "系统错误，请联系管理员", null, 0, "==《  " + e.getMessage() + " 》== " + getExceptionAllinformation(e));
    }

    public static String getExceptionAllinformation(Exception e) {
        String sOut = "";
        StackTraceElement[] trace = e.getStackTrace();
        sOut += "\t \r\n";
        for (StackTraceElement s : trace) {
            sOut += "\t " + s + "\r\n";
            // sOut += s;
        }
        return sOut;
    }

    /**
     * 判断哪些需要拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //这里是fastjson，因为配置了fastjson替代jackons

        //TODO 由于老接口有不少直接使用字符串返回，前端使用字符串接收，所以目前暂时不修改
//		return "com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter".equals(converterType.getCanonicalName())||
//				"org.springframework.http.converter.StringHttpMessageConverter".equals(converterType.getCanonicalName());

        return "com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter".equals(converterType.getCanonicalName());
    }

    /**
     * 返回值统一处理
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body != null && !(body instanceof PageResult) && body instanceof Page) {
            return new PageInfo((List) body);
        }
        return body;
    }
}