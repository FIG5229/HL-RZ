package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.integration.entity.RelationDiagramInfo;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.feign
 * @ClassName: ThemeFieldService
 * @Author: ztl
 * @Date: 2020-08-18
 * @Version: 1.0
 * @description:查询大类配置显示顺序
 */
@FeignClient(value="topology",fallbackFactory = ThemeFieldServiceFallbackFactory.class)
public interface ThemeFieldService {
    /**
     * 获取大类对应的配置标签中的显示顺序
     * @param typeId 大类ID
     * @param themeId 主题ID（默认传：1）
     * @return
     */
    @RequestMapping(value = "/themeField/getAttrDescByTypeId", method = RequestMethod.POST)
    List<String> getAttrDescByTypeId(@RequestParam("typeId") String typeId,@RequestParam("themeId") String themeId);
    
    /**
     * 根据CI_ID获取视图信息
     *
     * @param ciId
     * @return
     */
    @RequestMapping(value = "/getDiagramInfoByCiId", method = RequestMethod.GET)
    List<RelationDiagramInfo> getDiagramInfoByCiId(@RequestParam("ciId") String ciId, @RequestParam("ciCode") String ciCode);
}
