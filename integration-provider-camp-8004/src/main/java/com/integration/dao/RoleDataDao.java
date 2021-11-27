package com.integration.dao;

import com.integration.entity.RoleData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: RoleDataDao
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:角色代码与数据权限（CI大类）
 */
@Mapper
public interface RoleDataDao {

    /**
     * 根据角色代码和数据ID获取数据域权限对象
     *
     * @param roleDm 角色代码
     * @param dataId 数据ID
     * @return
     */
    RoleData getRoleDataByRoleDmAndDataId(@Param("roleDm") String roleDm, @Param("dataId") String dataId);

    /**
     * 新增数据权限
     * @param roleDataAdd 需新增数据权限列表
     * @return
     */
    int addRoleData(@Param("list") List<RoleData> roleDataAdd);

    /**
     * 修改数据权限
     *
     * @param roleDataUpdate 需修改数据权限列表
     * @return
     */
     int updateRoleData(@Param("list") List<RoleData> roleDataUpdate);

    /**
     *
     * @param tokenUserId 用户ID
     * @param dataId CI大类ID
     * @return
     */
    List<RoleData> findRoleDataByDataId(@Param("tokenUserId") String tokenUserId,@Param("dataId") String dataId);
}
