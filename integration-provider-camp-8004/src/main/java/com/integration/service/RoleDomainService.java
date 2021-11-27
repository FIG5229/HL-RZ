package com.integration.service;

import com.integration.entity.Org;
import com.integration.entity.RoleDomain;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: RoleDomainService
 * @Author: ztl
 * @Date: 2021-07-20
 * @Version: 1.0
 * @description:角色对应数据域关系
 */
public interface RoleDomainService {

    /**
     * 根据角色代码获取角色对应的数据域
     *
     * @param roleDm
     *
     * @return
     */
    RoleDomain getRoleDomainByRoleDm(String roleDm);

    /**
     * 保存角色对应的数据域
     *
     * @param roleDomain
     * @return
     */
    boolean saveRoleDomain(RoleDomain roleDomain);

    List<Org> findRoleDomainOrgList(String id);
}
