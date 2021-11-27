package com.integration.aop.log.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.integration.utils.DateUtils;
import com.integration.utils.JsonUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.integration.aop.log.entity.IomCampActionApiInfo;
import com.integration.aop.log.service.IomCampActionApiService;
import com.integration.dao.dameng.IomCampActionApiDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-26 04:00:36
 *
 * @version 1.0
 */
@Transactional
@Service
public class IomCampActionServiceImpl implements IomCampActionApiService{

	Logger log = LoggerFactory.getLogger(IomCampActionServiceImpl.class);

	@Resource
	private IomCampActionApiDao iIomCampActionDao;

	static Map<String, String> moduleMap = new HashMap<>();

	static {
		moduleMap.put("/camp/","基础管理");
		moduleMap.put("/cmdb/","配置管理");
		moduleMap.put("/alarm/","事件可视化");
		moduleMap.put("/dcv/","数据中心可视化");
		moduleMap.put("/dmv/","架构可视化");
		moduleMap.put("/pmv/","性能可视化");
		moduleMap.put("/smv/","场景配置化");
		moduleMap.put("/websocket/","消息服务");
		moduleMap.put("/interface/","数据采集监控");
	}
	
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Override 
	public void insertInfo(IomCampActionApiInfo info){
	
		iIomCampActionDao.insertInfo(info);
	}

	@Override
	public String saveAction(String path, Object o, String result, String userId, String userDlzh, String userName) {
		try{
			String id = SeqUtil.nextId() +"";
			IomCampActionApiInfo campAction = new IomCampActionApiInfo();
			campAction.setId(id);
			campAction.setLog_time(DateUtils.getNowStr());
			campAction.setUser_id(userId);
			campAction.setCzry_dldm(userDlzh);
			campAction.setCzry_mc(userName);
			campAction.setOp_name(dealModuleName(path));
			campAction.setOp_path(path);
			//campAction.setOp_desc(desc);
			campAction.setIs_status(0);
			String op_param = JSONObject.toJSONString(o);
			campAction.setOp_param(op_param);
			campAction.setOp_result(result);
			campAction.setCjsj(DateUtils.getNowStr());
			iIomCampActionDao.insertInfo(campAction);

			return id;
		}catch (Exception e){
			//e.printStackTrace();

			log.error("记录错误日志出现错误",e);
		}

		return null;
	}

	private static String dealModuleName(String path){
		List<String> keys = moduleMap.keySet().stream().filter(item -> path.contains(item)).collect(Collectors.toList());
		if(!keys.isEmpty()){
			return moduleMap.get(keys.get(0));
		}
		return path;
	}
}