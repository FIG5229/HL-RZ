package com.integration.service;

import com.integration.entity.Org;

import java.util.List;
/**
 * 组织机构Service
 * @author zhuxd
 * @date 2019-07-31
 */
public interface OrgService {
    /**
     * 添加组织机构
     * @param org 组织机构
     * @return int
     */
    int addOrg(Org org);

    /**
     * 修改组织机构
     * @param org 组织机构
     * @return int
     */
    int updateOrg(Org org);

    /**
     * 根据id逻辑删除组织机构
     * @param id 机构ID
     * @return int
     */
    int blockUpOrg(String id);

    /**
     * 根据id物理删除组织机构
     * @param id 机构ID
     * @return int
     */
    int deleteOrg(String id);

    /**
     * 按层级返回组织机构列表
     * @param id 机构ID
     * @return 层级组织机构列表
     */
    List<Org> findOrgList(String id);

    /**
     * 根据传入部门ID获取部门所在机构ID
     * @param id 部门ID
     * @return String 机构ID
     */
    String findOrgByDept(String id);

    /**
     * 查询组织机构信息列表
     * 业务功能展示组织机构功能使用
     * @param id
     * @return List<Org>
     */
    List<Org> findOrgListForBusi(String id);

    /**
     * 查询最大级
     * @return List<Org>
     */
    List<Org> findAllMax();

    /**
     * 查询下一级
     * @param pid 上级机构ID
     * @return List<Org>
     */
    List<Org> findNext(String pid);

    /**
     * 查询单个组织机构信息
     * @param id 机构ID
     * @return Org
     */
    Org findOrg(String id);

    /**
     * 查询是否存在下级单位
     * @param id 机构ID
     * @return int
     */
    boolean isHasChildOrg(String id);

    /**
     * 查询是否存在部门信息
     * @param id 机构ID
     * @return int
     */
    boolean isHasDept(String id);
    /**
     * 查询部门下是否存在系统用户信息
     * @param id 部门ID
     * @return int 人员数量
     */
    boolean isHasSysUser(String id);
    /**
     * 根据上级机构ID查询排序号
     * @param pid 上级机构ID
     * @return String 序号
     */
    String getSort(String pid);
    /**
     * 查询同一级别节点下是否存在同名节点
     *
     * @param org
     * @return 相同节点数量
     */
    int isHasSameName(Org org);
    /**
     * 根据组织机构ID查询部门标志
     * @param id 机构ID
     * @return
     */
    int findIsDept(String id);

    /**
     * 校验数据域是否重复
     *
     * @param dataDomain
     * @return
     */
    int checkDataDomain(String dataDomain,String id);

    List<Org> findOrgAndUserList(String id);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有组织机构数据
     */
    List<Org> getAllOrgData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 处理同步组织机构数据
     */
    void handleOrgData(List<Org> orgList);
}
