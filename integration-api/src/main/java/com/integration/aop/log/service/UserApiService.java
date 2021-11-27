package com.integration.aop.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integration.dao.dameng.IomCampCzryApiDao;

/**
 * 获取人员信息
 * 
 * @author dell
 *
 */
@Service
public class UserApiService {

	@Autowired
	private IomCampCzryApiDao czryApiDao;
	
	/**
	 * 通过人员id获取人员名称
	 * 
	 * @param userId
	 * @return
	 */
	public String getUserNameById(String userId) {
		return czryApiDao.getCzryMcById(userId);
	}
}
