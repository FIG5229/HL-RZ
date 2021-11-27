package com.integration.controller;

import com.integration.entity.PageResult;
import com.integration.feign.AlarmService;
import com.integration.feign.PmvService;
import com.integration.service.TypeItemService;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 配置查询controller
 * @author zhuxd
 * @date 2019-09-20 10:35:00
 *
 */
@RestController
@RequestMapping("/configQuery")
public class ConfigQueryController {
    /**
     * 告警管理service
     */
    @Autowired
    AlarmService alarmService;
    /**
     * 性能管理service
     */
    @Autowired
    PmvService pmvService;
    /**
     * ci管理service
     */
    @Autowired
    private TypeItemService typeItemService;
    /**
     * 日志工具类
     */
    private static final Logger logger = LoggerFactory.getLogger(ConfigQueryController.class);


    /**
     * 获取告警信息
     *
     * @param ciId ciId
     * @return
     */
    @RequestMapping(value = "/findAlarmInfo", method = RequestMethod.POST)
    public List<Map<String, Object>> findAlarmList(String ciId,String ciCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ciid", ciId);
        map.put("ciCode", ciCode);
        return alarmService.getEmvEvCurrentInfoByciid(map);
    }

    /**
     * 获取性能信息
     *
     * @param ciId ciId
     * @return
     */
    @RequestMapping(value = "/findPerformanceInfo", method = RequestMethod.GET)
    public List findPerformanceList(String ciId) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("ci_id", ciId);
        return pmvService.findPerformanceList(hashMap);
    }

    /**
     * 查询配置和告警信息
     *
     * @param ciId ciId
     * @param tId  ci大类Id
     * @return 配置和告警信息
     */
    @RequestMapping(value = "/findConfigAndAlarmInfo", method = RequestMethod.GET)
    public Map<String, Object> findConfigAndAlarmInfo(String ciId, String tId) {
        Map<String, Object> alarmParamMap = new HashMap<String, Object>();
        alarmParamMap.put("ciid", ciId);
        //根据ciId查询告警信息
        List<Map<String, Object>> alarmList = alarmService.getEmvEvCurrentInfoByciid(alarmParamMap);

        //配置信息参数map
        Map<String, String> configParamMap = new HashMap<String, String>();
        configParamMap.put("ci_id", ciId);
        configParamMap.put("tid", tId);
        //根据ciId查询配置信息
        Map configDataMap = null;
        Map<String, Object> configHeadMap = null;
        List<Map> configList = typeItemService.getAlarmCiInfo(configParamMap);
        if (configList != null && configList.size() > 0) {
            configDataMap = configList.get(0);
            //配置信息标题
            configHeadMap = new HashMap<String, Object>();
            configHeadMap.put("ciTypeName", configDataMap.get("大类名称"));
            configHeadMap.put("ciName", configDataMap.get("CI编码"));
            
            //利用迭代器把"大类名称"和"CI编码"在map中删除
            Iterator<String> iter = configDataMap.keySet().iterator();
            while (iter.hasNext()) {
            	String key = iter.next();
            	if ("大类名称".equals(key)) {
            		iter.remove();
            	}
            	if ("CI编码".equals(key)) {
            		iter.remove();
            	}
            }
        }
        //返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        //告警信息列表
        map.put("alarmList", alarmList);
        //配置信息体
        map.put("configHead", configHeadMap);
        //配置信息体
        map.put("configInfo", configDataMap);
        //告警数量
        map.put("alarmNum", alarmList.size());
        PageResult.setMessage(true, "查询成功");

        return map;
    }
}
