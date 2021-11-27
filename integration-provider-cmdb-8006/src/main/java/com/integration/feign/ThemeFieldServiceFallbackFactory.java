package com.integration.feign;

import com.integration.controller.RelController;
import com.integration.entity.RelationDiagramInfo;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: ThemeFieldServiceFallbackFactory
 * @Author: ztl
 * @Date: 2020-08-18
 * @Version: 1.0
 * @description:
 */
@Component
public class ThemeFieldServiceFallbackFactory implements FallbackFactory<ThemeFieldService> {
    private final static Logger logger = LoggerFactory.getLogger(ThemeFieldServiceFallbackFactory.class);
    @Override
    public ThemeFieldService create(Throwable throwable) {
        logger.error("调用查询大类配置显示顺序时异常:",throwable);
        return new ThemeFieldService(){
            @Override
            public List<String> getAttrDescByTypeId(String typeId, String themeId) {
                return null;
            }

			@Override
			public List<RelationDiagramInfo> getDiagramInfoByCiId(String ciId, String ciCode) {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }
}
