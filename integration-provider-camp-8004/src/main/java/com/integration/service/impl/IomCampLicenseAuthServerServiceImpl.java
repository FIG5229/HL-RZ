package com.integration.service.impl;

import com.integration.dao.IomCampLicenseAuthServerMapper;
import com.integration.entity.IomCampLicenseAuthServer;
import com.integration.service.IomCampLicenseAuthServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
* @Package: com.integration.service.impl
* @ClassName: IomCampLicenseAuthServerServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 权限管理
*/
@Service
public class IomCampLicenseAuthServerServiceImpl implements IomCampLicenseAuthServerService {

    @Resource
    private IomCampLicenseAuthServerMapper authServerMapper;

    @Override
    public List<IomCampLicenseAuthServer> selectList(IomCampLicenseAuthServer iomCampLicenseAuthServer) {

        return authServerMapper.selectList(iomCampLicenseAuthServer);
    }


    @Override
    public boolean deleteByServerIp(String ip) {
        return authServerMapper.deleteByServerIp(ip) > 0;
    }
}
