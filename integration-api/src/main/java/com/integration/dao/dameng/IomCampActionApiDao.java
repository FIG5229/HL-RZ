package com.integration.dao.dameng;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.integration.aop.log.entity.IomCampActionApiInfo;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-12-26 04:00:36
 * @version 1.0
 */
 @Mapper
 public interface IomCampActionApiDao {
 
	/**
	 * 新增单条记录
	 * @param info
	 */
	@Insert({
		"insert into IOMMGT.IOM_CAMP_ACTION(id,log_time,user_id,czry_dldm,czry_mc,op_name,op_path,op_desc,"
		+ "is_status,op_param,op_result,op_class,op_method,cjsj)",
		"values(#{id},#{log_time},#{user_id},#{czry_dldm},#{czry_mc},#{op_name},#{op_path},#{op_desc},#{is_status},"
		+ "#{op_param},#{op_result},#{op_class},#{op_method},#{cjsj})"
	})
	public void insertInfo(IomCampActionApiInfo info);
 }