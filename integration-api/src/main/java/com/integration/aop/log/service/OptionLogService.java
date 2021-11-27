package com.integration.aop.log.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import com.integration.aop.log.annotations.OptionLog;
import com.integration.aop.log.entity.IomCampActionApiInfo;
import com.integration.config.Advice.ErrorControllerAdviceBase;
import com.integration.utils.DataUtils;
import com.integration.utils.DateUtils;
import com.integration.utils.JsonUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.SpringAopUtil;
import com.integration.utils.token.TokenUtils;
import com.integration.utils.web.ServletUtil;

/**
 * 记录日志
 * 
 * @author dell
 *
 */
@Aspect
@Component
public class OptionLogService {
	private static final Logger logger = LoggerFactory.getLogger(OptionLogService.class);
	@Pointcut("@annotation(com.integration.aop.log.annotations.OptionLog)")
	private void around(){}
	
	@Autowired
	private IomCampActionApiService campActionService;
	@Autowired
	private UserApiService userApiService;
	
	@Around("around()")
	public Object dealPointCut(ProceedingJoinPoint joinPoint){
		HttpServletRequest request = ServletUtil.getRequest();
		String userId = TokenUtils.getTokenUserId();
		String userDlzh = TokenUtils.getTokenDldm();
		String userName = null;
		if(userId.equals("-1")){
			userName = "system";
		} else {
			userName = userApiService.getUserNameById(userId);
		}
		String id = SeqUtil.nextId() +"";
		String path = request.getServletPath();
		Map<String, String[]> paramM = request.getParameterMap();
		
		/*
		 * 获取注解
		 */
		OptionLog optionLog = SpringAopUtil.getAnnotationInMethod(joinPoint, OptionLog.class);
		String module = optionLog.module();
		String desc = optionLog.desc();
		boolean writeParam = optionLog.writeParam();
		boolean writeResult = optionLog.writeResult();
		
		Object[] args = joinPoint.getArgs();
		Object result = null;
		boolean success = true;
		try {
			result = joinPoint.proceed(args);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			result = DataUtils.returnPr(false, "接口异常");
			success = false;
		}
		
		IomCampActionApiInfo campAction = new IomCampActionApiInfo();
		campAction.setId(id);
		campAction.setLog_time(DateUtils.getNowStr());
		campAction.setUser_id(userId);
		campAction.setCzry_dldm(userDlzh);
		campAction.setCzry_mc(userName);
		campAction.setOp_name(module);
		campAction.setOp_path(path);
		campAction.setOp_desc(desc);
		campAction.setIs_status(success ? 1 : 0);
		if(writeParam) {
			campAction.setOp_param(JsonUtils.mapToString(paramM));
		}
		if(writeResult) {
			campAction.setOp_result(JsonUtils.objectToString(result));
		}
		campAction.setCjsj(DateUtils.getNowStr());
		//停掉切面日志记录，每个服务都会调用这个方法，会导致id冲突
		//campActionService.insertInfo(campAction);
		
		return result;
	}
}
