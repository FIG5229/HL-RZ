package com.integration.service;

import com.integration.entity.IomCampLicenseAuthServer;

import java.util.List;
/**
* @Package: com.integration.service
* @ClassName: IomCampLicenseAuthServerService
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 模块授权管理
*/
public interface IomCampLicenseAuthServerService {

    List<IomCampLicenseAuthServer> selectList(IomCampLicenseAuthServer iomCampLicenseAuthServer);

    /**
     * 通过ip删除server
     * @param ip
     * @return
     */
    boolean deleteByServerIp(String ip);
}
