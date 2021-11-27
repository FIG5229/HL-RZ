package com.integration.config.Advice;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.integration.config.DateConverter;

/**
 * @author zfh
 * @version 1.0
 * @since 2019/1/4 15:23
 */
//@ControllerAdvice
public class ControllerHandlerBase {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(new DateConverter());
        }
    }
}