package com.integration.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
/**
* @Package: com.integration.dao
* @ClassName: ActionsDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 操作日志
*/
@Mapper
public interface ActionsDao {


	/**
	 * 保存操作日志
	 */
	public void insertAction(Map ac);

}
