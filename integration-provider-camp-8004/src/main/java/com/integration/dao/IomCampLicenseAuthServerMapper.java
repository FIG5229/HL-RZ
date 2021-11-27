package com.integration.dao;

import com.integration.entity.IomCampLicenseAuthServer;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
/**
* @Package: com.integration.dao
* @ClassName: IomCampLicenseAuthServerMapper
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 权限管理
*/
@Mapper
public interface IomCampLicenseAuthServerMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(IomCampLicenseAuthServer record);

    int insertSelective(IomCampLicenseAuthServer record);

    IomCampLicenseAuthServer selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(IomCampLicenseAuthServer record);

    int updateByPrimaryKey(IomCampLicenseAuthServer record);

    List<IomCampLicenseAuthServer> selectList(IomCampLicenseAuthServer record);

    int deleteByServerIp(String ip);
}