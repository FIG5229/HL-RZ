package com.integration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.IomCampActionInfo;
import com.integration.entity.PageResult;
import com.integration.mybatis.entity.Page;
import com.integration.service.ActionService;
import com.integration.service.IomCampActionLogService;
import com.integration.utils.DataUtils;
import com.integration.utils.PageUtils;
/**
* @Package: com.integration.controller
* @ClassName: ActionLogConntroller
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 操作日志
*/
@RestController
public class ActionLogConntroller {

	@Resource
	public IomCampActionLogService iomCampActionLogService;
	@Resource
	public ActionService actionService;


	private final static Logger logger = LoggerFactory.getLogger(ActionLogConntroller.class);

	/**
	 * 分页查询操作日志
	 *
	 * @param params
	 * @return
	 */
	@Deprecated
	public PageResult getActionLog1(@RequestParam Map<String, Object> params) {
		List<IomCampActionInfo> retMessage = new ArrayList<IomCampActionInfo>();
		Page page = PageUtils.getPage();
		params.put("page", page);
		try {
			retMessage = iomCampActionLogService.getAllPage(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("获取操作日志分页："+e.getMessage());
		}
		return DataUtils.returnPr(page.getTotalResult(), retMessage);
	}

	/**
	 * 分页查询操作日志
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/getActionLog", method = RequestMethod.GET)
	public PageResult getActionLog(@RequestParam Map<String, Object> params) {
		Page page = PageUtils.getPage();
		params.put("page", page);
		return iomCampActionLogService.getAllPageNew(params);
	}


	@RequestMapping(value = "/insertActionfeign", method = RequestMethod.POST)
	public void insertActionfeign(@RequestBody String ac) {
		Map acs=JSONObject.parseObject(ac,Map.class);
		actionService.insertAction(acs);
	}
}
