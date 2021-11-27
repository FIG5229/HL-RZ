package com.integration.dao;

import com.integration.entity.RoleDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: RoleDomainDao
 * @Author: ztl
 * @Date: 2021-07-20
 * @Version: 1.0
 * @description:角色对应数据域关系
 */
@Mapper
public interface RoleDomainDao {

    RoleDomain getRoleDomainByRoleDm(String roleDm);

    boolean updateRoleDomain(RoleDomain roleDomain);

    boolean addRoleDomain(RoleDomain roleDomain);

    boolean deleteRoleDomain(String id);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 根据角色代码获取所有数据域
     */
    List<String> getRoleDomainByRoleDmList(@Param("list") List<String> roleDmList);
}
