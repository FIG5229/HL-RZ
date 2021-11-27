package com.integration.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.feign
* @ClassName: DictService
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 字典管理 8004
*/
@FeignClient(value="camp",fallbackFactory = DictServiceFallbackFactory.class)
public interface DictService {

    /**
     * 获取告警信息
     * @param sjId
     * @return
     */
    @RequestMapping(value = "/findDiceBySjIdHump", method = RequestMethod.GET)
    List<Map<String, Object>> findDiceBySjIdHump(@RequestParam("sjId") String sjId);

    @RequestMapping(value = "/addDictHumps", method = RequestMethod.POST)
    Object addDictHumps(@RequestParam("dicts") String dicts);
    /**
     * 查询上级ID获取字典信息
     *
     * @param sj_id
     * @return
     */
    @RequestMapping(value = "/findDiceBySjId", method = RequestMethod.GET)
    List<Map<String,Object>> findDiceBySj_id(@RequestParam("sj_id") int sj_id);

}
