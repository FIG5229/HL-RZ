package com.integration.service;

import com.integration.entity.RoleData;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: RoleDataService
 * @Author: ztl
 * @Date: 2020-06-10
 * @Version: 1.0
 * @description:角色代码与数据域权限（CI大类）
 */
public interface RoleDataService {
    /**
     *查询数据权限列表
     * @param roleDm 角色代码
     * @param typeName 模糊查询条件
     * @return
     */
     List<RoleData> findRoleDataList(String roleDm, String typeName);


    /**
     * 保存或更新 角色对应的数据权限
     * @param roleDataList 数据权限列表
     * @param roleDm 角色代码
     * @return 返回是否成功对象
     */
    boolean updateRoleData(String roleDataList, String roleDm);

    /**
     * 根数CI大类ID获取数据权限
     * @param dataId CI大类ID
     * @return
     */
    RoleData findRoleDataByDataId(String dataId);

    /**
     * 获取当前登录用户最高数据权限列表
     * @return
     */
    List<RoleData> findRoleHighDataList();
}
