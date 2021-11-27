package com.integration.aop.log.service;

import com.integration.aop.log.entity.IomCampActionApiInfo;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-26 04:00:36
 * @version 1.0
 */
 
 public interface IomCampActionApiService {
 	
	/**
	 * 新增单条记录
	 * @param info
	 */
	public void insertInfo(IomCampActionApiInfo info);

	/**
	 * 新增记录
	 */
	public String saveAction(String path, Object o, String result, String userId, String userDlzh, String userName);
 }