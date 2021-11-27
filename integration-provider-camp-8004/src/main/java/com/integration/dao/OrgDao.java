package com.integration.dao;

import com.integration.entity.Org;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 组织机构Dao
 * @author zhuxd
 * @date 2019-07-31
 */
@Mapper
@Repository
public interface OrgDao {

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
     int blockUpOrg(@Param("id")String id);

    /**
     * 根据id物理删除组织机构
     * @param id 机构ID
     * @return int
     */
     int deleteOrg(@Param("id")String id);

    /**
     * 查询组织机构信息列表
     * 组织机构管理功能使用
     * @param id 机构ID
     * @return List<Org>
     */
    List<Org> findOrgList(@Param("id")String id);
    /**
     * 查询组织机构信息列表
     * 业务功能展示组织机构功能使用
     * @param id 机构ID
     * @return List<Org>
     */
    List<Org> findOrgListForBusi(@Param("id")String id);

    /**
     * 查询父级组织机构列表
     * @param id
     * @return List<Org> 组织机构列表
     */
    List<Org> findOrgParentList(@Param("id")String id);
    /**
     * 根据部门ID获取机构信息
     * @param id 部门ID
     * @return Org 机构信息
     */
    Org findOrgByDept(@Param("id")String id);
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
     List<Org> findNext(@Param("pid")String pid);

    /**
     * 查询单个组织机构信息
     * @param id 机构ID
     * @return Org
     */
     Org findOrg(@Param("id")String id);

    /**
     * 查询是否存在下级单位
     * @param id 机构ID
     * @return int
     */
     int isHasChildOrg(@Param("id")String id);

    /**
     * 查询是否存在部门信息
     * @param id 机构ID
     * @return int
     */
     int isHasDept(@Param("id")String id);
    /**
     * 查询部门下是否存在人员信息
     * @param id 部门ID
     * @return int 人员数量
     */
    int isHasSysUser(@Param("id")String id);

    /**
     * 根据上级机构ID查询排序号
     * @param pid 上级机构ID
     * @return String 序号
     */
     String getSort(@Param("pid") String pid);

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
    int findIsDept(@Param("id")String id);

    List<Org> findRoleDomainOrgList(String id);

    Org findRoleDomainOrg(String id);

    List<Org> findRoleDomainAllMax();

    int checkDataDomain(@Param("dataDomain") String dataDomain,@Param("id") String id);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有组织机构数据
     */
    List<Org> getAllOrgData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 删除所有组织机构数据
     */
    void deleteAllOrgData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 批量增加组织机构数据
     */
    void saveAllOrgData(List<Org> orgList);
}
