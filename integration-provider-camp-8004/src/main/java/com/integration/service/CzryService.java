package com.integration.service;

import java.util.List;
import java.util.Map;

import com.integration.entity.Czry;
import com.integration.generator.entity.IomCampCzry;

/**
* @Package: com.integration.service
* @ClassName: CzryService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 用户管理
*/
public interface CzryService {
    /**
     * 添加用户
     */
    public int addCzry(Czry czry, String[] roleIds, String cjrId);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public int deleteCzry(String id);

    /**
     * 修改用户
     *
     * @param czry
     * @param roleIds
     * @return
     */
    public int updateCzry(Czry czry, String[] roleIds, String cjrId);

    /**
     * 查询用户集合
     *
     * @param search
     * @param pageCount
     * @param pageIndex
     * @return
     */
    public List<Czry> findCzryList(String org_id, String dept_id, String search, Integer pageCount, Integer pageIndex);

    /**
     * 查询用户总数
     *
     * @return
     */
    public int findCzryListCount(String org_id, String dept_id, String search);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    public Czry findCzryById(String id);

    /**
     * 登录
     *
     * @param czrDldm
     * @param czryPass
     * @return
     */
    public Czry login(String czrDldm, String czryPass);

    /**
     * 修改状态
     *
     * @param id
     * @param statu
     * @return
     */
    public int updateStatus(Integer id, Integer statu);

    /**
     * 重置密码
     *
     * @param czry
     * @return
     */
    public int resetPWDCzry(Czry czry);

    /**
     * 通过id串查询用户名
     *
     * @param ids
     * @return
     */
    public List<IomCampCzry> findCzryByIds(List<String> ids);
    /**
     * 通过id串查询用户名包含 已删除用户
     *
     * @param ids
     * @return
     */
    public List<IomCampCzry> findCzryByIdsWithDelete(List<String> ids);

    /**
     * 校验输入信息是否正确
     * @param czry
     * @return
     */
    public String checkCzry(Czry czry);

    /**
     * 查询所有操作人员
     * @param nameOrId
     * @return
     */
    public List<Map<String, Object>> getCzrysAll(String nameOrId);

    /**
     * 根据操作人id，查询操作人
     * @param listStr
     * @return
     */
    public List<Map<String, Object>> getCzrysNameById(String[] listStr);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 根据登录人ID获取所有配置角色
     */
    List<String> getRoleIdByCzryId(String id);
}
