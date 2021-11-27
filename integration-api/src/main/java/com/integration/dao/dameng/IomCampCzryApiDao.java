package com.integration.dao.dameng;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IomCampCzryApiDao {

	/**
	 * 通过人员id获取人员名称
	 * 
	 * @param userId
	 * @return
	 */
	@Select({
		"select czry_mc from IOMMGT.IOM_CAMP_CZRY where id=#{userId}"
	})
	String getCzryMcById(@Param("userId")String userId);
}
