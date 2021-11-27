package com.integration.service;

import com.integration.entity.IomCampLicenseAuth;

import java.util.List;
/**
* @Package: com.integration.service
* @ClassName: IomCampLicenseAuthService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 模块授权实体类
*/
public interface IomCampLicenseAuthService {

    List<IomCampLicenseAuth> selectList(IomCampLicenseAuth record);

    IomCampLicenseAuth get(IomCampLicenseAuth record);

    String getClientCode();

    boolean register(IomCampLicenseAuth record);
}
