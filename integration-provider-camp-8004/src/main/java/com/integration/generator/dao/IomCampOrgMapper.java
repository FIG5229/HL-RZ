package com.integration.generator.dao;

import com.integration.generator.entity.IomCampOrg;
import com.integration.generator.entity.IomCampOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampOrgMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 组织机构管理
*/
public interface IomCampOrgMapper {
    long countByExample(IomCampOrgExample example);

    int deleteByExample(IomCampOrgExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCampOrg record);

    int insertSelective(IomCampOrg record);

    List<IomCampOrg> selectByExample(IomCampOrgExample example);

    IomCampOrg selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCampOrg record, @Param("example") IomCampOrgExample example);

    int updateByExample(@Param("record") IomCampOrg record, @Param("example") IomCampOrgExample example);

    int updateByPrimaryKeySelective(IomCampOrg record);

    int updateByPrimaryKey(IomCampOrg record);
}