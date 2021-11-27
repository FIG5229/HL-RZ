package com.integration.dao;

import com.integration.entity.IomCampLicenseAuth;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
/**
* @Package: com.integration.dao
* @ClassName: IomCampLicenseAuthMapper
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 权限管理
*/
@Mapper
public interface IomCampLicenseAuthMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(IomCampLicenseAuth record);

    int insertSelective(IomCampLicenseAuth record);

    IomCampLicenseAuth selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(IomCampLicenseAuth record);

    int updateByPrimaryKeyWithBLOBs(IomCampLicenseAuth record);

    int updateByPrimaryKey(IomCampLicenseAuth record);

    List<IomCampLicenseAuth> selectList(IomCampLicenseAuth record);
}