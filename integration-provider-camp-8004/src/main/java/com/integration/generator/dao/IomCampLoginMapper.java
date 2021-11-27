package com.integration.generator.dao;

import com.integration.generator.entity.IomCampLogin;
import com.integration.generator.entity.IomCampLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampLoginMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 登录管理
*/
public interface IomCampLoginMapper {
    long countByExample(IomCampLoginExample example);

    int deleteByExample(IomCampLoginExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCampLogin record);

    int insertSelective(IomCampLogin record);

    List<IomCampLogin> selectByExample(IomCampLoginExample example);

    IomCampLogin selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCampLogin record, @Param("example") IomCampLoginExample example);

    int updateByExample(@Param("record") IomCampLogin record, @Param("example") IomCampLoginExample example);

    int updateByPrimaryKeySelective(IomCampLogin record);

    int updateByPrimaryKey(IomCampLogin record);
}