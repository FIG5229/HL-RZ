package com.integration.service.impl;

import com.integration.dao.CzryDao;
import com.integration.dao.OrgDao;
import com.integration.entity.Org;
import com.integration.entity.Org;
import com.integration.rabbit.Sender;
import com.integration.service.OrgService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 组织机构ServiceImpl
 *
 * @author zhuxd
 * @date 2019-07-31
 */
@Service
public class OrgServiceImpl implements OrgService {
    private final static Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);
    @Autowired
    private OrgDao orgDao;
    @Resource
    private CzryDao czryDao;
    @Autowired
    private Sender sender;
    /**
     * 添加组织机构
     *
     * @param org 组织机构
     * @return int
     */
    @Override
    public int addOrg(Org org) {
        int result = orgDao.addOrg(org);
        try {
            sender.sendDomainAndOrgData();
        }catch (Exception e){
            logger.error("同步数据域及组织机构数据异常",e);
        }
        return result;
    }

    /**
     * 修改组织机构
     *
     * @param org 组织机构
     * @return int
     */
    @Override
    public int updateOrg(Org org) {
        int result = orgDao.updateOrg(org);
        try {
            sender.sendDomainAndOrgData();
        }catch (Exception e){
            logger.error("同步数据域及组织机构数据异常",e);
        }
        return result;
    }

    public OrgServiceImpl() {
        super();
    }

    /**
     * 根据id逻辑删除组织机构
     *
     * @param id 机构ID
     * @return int
     */
    @Override
    public int blockUpOrg(String id) {
        return orgDao.blockUpOrg(id);
    }

    /**
     * 根据id物理删除组织机构
     *
     * @param id 机构ID
     * @return int
     */
    @Override
    public int deleteOrg(String id) {
        int result = orgDao.deleteOrg(id);
        try {
            sender.sendDomainAndOrgData();
        }catch (Exception e){
            logger.error("同步数据域及组织机构数据异常",e);
        }
        return result;
    }

    /**
     * 按层级返回组织机构列表
     * @param id 机构ID
     * @return 层级组织机构列表
     */
    @Override
    public List<Org> findOrgList(String id) {
        //所有组织机构列表
        List<Org> orgList = orgDao.findOrgList(id);
        //根节点
        List<Org> rootOrg = new ArrayList<>();
        //将当前登录用户的机构ID作为根节点
        if (StringUtils.isNotEmpty(id)) {
            Org org = orgDao.findOrg(id);
            rootOrg.add(org);
        } else {
            //获取所有根节点pid 为0的节点
            rootOrg = orgDao.findAllMax();
        }
        /* 根据Org类的order排序 */
        Collections.sort(rootOrg, order());
        // 为根菜单设置子菜单，getClild是递归调用的
        for (Org org : rootOrg) {
            /* 获取根节点下的所有子节点 使用getChild方法 */
            List<Org> childList = getChild(org.getId(), orgList);
            // 给根节点设置子节点
            org.setChildren(childList);
        }
        return rootOrg;
    }
    /**
     * 根据传入部门ID获取部门所在机构ID
     * @param id 部门ID
     * @return String 机构ID
     */
    @Override
    public String findOrgByDept(String id){
        Org org = orgDao.findOrgByDept(id);
        String orgId ="";
        if(org !=null){
            orgId=org.getId();
        }
        /*返回机构ID*/
        return orgId ;
    }

    /**
     * 查询组织机构信息列表
     * 业务功能展示组织机构功能使用
     * @param id
     * @return List<Org>
     */
    @Override
    public List<Org> findOrgListForBusi(String id) {
        /**组织机构列表**/
        List<Org> orgList =orgDao.findOrgListForBusi(id);

        //按层级组织菜单列表
        List<Org> rootOrg = new ArrayList<>();

        if(orgList!=null && !orgList.isEmpty()){
            /**查询时保证根节点在最上边**/
            Org org  = orgList.get(0);
            rootOrg.add(org);
        }

        /* 根据Org类的order排序 */
        Collections.sort(rootOrg, order());

        // 为根菜单设置子菜单，getClild是递归调用的
        for (Org org : rootOrg) {
            /* 获取根节点下的所有子节点 使用getChild方法 */
            List<Org> childList = getChild(org.getId(), orgList);
            // 给根节点设置子节点
            org.setChildren(childList);
        }
        return rootOrg;
    }

    /**
     * 查询最大级
     *
     * @return List<Org>
     */
    @Override
    public List<Org> findAllMax() {
        return orgDao.findAllMax();
    }

    /**
     * 查询下一级
     *
     * @param pid 上级机构ID
     * @return List<Org>
     */
    @Override
    public List<Org> findNext(String pid) {

        return orgDao.findNext(pid);
    }

    /**
     * 查询单个组织机构信息
     *
     * @return Org 组织机构
     */
    @Override
    public Org findOrg(String id) {
        return orgDao.findOrg(id);
    }

    /**
     * 查询是否存在下级单位
     *
     * @param id 机构ID
     * @return int
     */
    @Override
    public boolean isHasChildOrg(String id) {
        int count = 0;
        count = orgDao.isHasChildOrg(id);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询是否存在部门信息
     *
     * @param id 机构ID
     * @return int
     */
    @Override
    public boolean isHasDept(String id) {
        int count = 0;
        count = orgDao.isHasDept(id);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 查询部门下是否存在系统用户信息
     * @param id 部门ID
     * @return int 人员数量
     */
    @Override
    public boolean isHasSysUser(String id) {
        int count = 0;
        count = orgDao.isHasSysUser(id);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 排序,根据sort排序
     * @return comparator
     */
    public Comparator<Org> order(){
        Comparator<Org> comparator = new Comparator<Org>() {
            @Override
            public int compare(Org o1, Org o2) {
                if(o1.getSort() != o2.getSort()){
                    return Integer.parseInt(o1.getSort()) - Integer.parseInt(o2.getSort());
                }
                return 0;
            }
        };
        return comparator;
    }

    /**
     * 获取子节点列表
     *
     * @param id     父节点id
     * @param allOrgList 所有组织机构列表
     * @return List<Org> 每个根节点下，所有子节点列表
     */
    public List<Org> getChild(String id, List<Org> allOrgList) {
        // 子菜单
        List<Org> childList = new ArrayList<Org>();
        for (Org org : allOrgList) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            // 相等说明：为该根节点的子节点。
            if (org.getPid().equals(id)) {
                childList.add(org);
            }
        }
        // 递归
        for (Org org : childList) {
            org.setChildren(getChild(org.getId(),allOrgList));
        }
        // 排序
        Collections.sort(childList, order());
        // 如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Org>();
        }
        return childList;
    }
    /**
     * 根据上级机构ID查询排序号
     * @param pid 上级机构ID
     * @return String 序号
     */
    @Override
    public String getSort(String pid) {
        return orgDao.getSort(pid);
    }
    /**
     * 查询同一级别节点下是否存在同名节点
     *
     * @param org
     * @return int 相同节点数量
     */
    @Override
    public int isHasSameName(Org org) {
        return orgDao.isHasSameName(org);
    }
    /**
     * 根据组织机构ID查询部门标志
     * @param id 机构ID
     * @return int 部门标识
     */
    @Override
    public int findIsDept(String id) {
        return orgDao.findIsDept(id);
    }

    @Override
    public int checkDataDomain(String dataDomain,String id) {
        return orgDao.checkDataDomain(dataDomain,id);
    }

    @Override
    public List<Org> findOrgAndUserList(String id) {
        //所有组织机构列表
        List<Org> orgList = orgDao.findOrgList(id);
        //根节点
        List<Org> rootOrg = new ArrayList<>();
        //将当前登录用户的机构ID作为根节点
        if (StringUtils.isNotEmpty(id)) {
            Org org = orgDao.findOrg(id);
            rootOrg.add(org);
        } else {
            //获取所有根节点pid 为0的节点
            rootOrg = orgDao.findAllMax();
        }
        /* 根据Org类的order排序 */
        Collections.sort(rootOrg, order());
        // 为根菜单设置子菜单，getClild是递归调用的
        for (Org org : rootOrg) {
            /* 获取根节点下的所有子节点 使用getChild方法 */
            List<Org> childList = getChildAndUser(org.getId(), orgList);
            // 给根节点设置子节点
            org.setChildren(childList);
        }
        return rootOrg;
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有组织机构数据
     */
    @Override
    public List<Org> getAllOrgData() {
        return orgDao.getAllOrgData();
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 处理同步组织机构数据
     */
    @Override
    public void handleOrgData(List<Org> orgList) {
        orgDao.deleteAllOrgData();
        orgDao.saveAllOrgData(orgList);
    }

    /**
     * 获取子节点列表
     *
     * @param id     父节点id
     * @param allOrgList 所有组织机构列表
     * @return List<Org> 每个根节点下，所有子节点列表
     */
    public List<Org> getChildAndUser(String id, List<Org> allOrgList) {
        // 子菜单
        List<Org> childList = new ArrayList<Org>();
        for (Org org : allOrgList) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            // 相等说明：为该根节点的子节点。
            if (org.getPid().equals(id)) {
                childList.add(org);
            }
        }
        // 递归
        for (Org org : childList) {
            org.setChildren(getChildAndUser(org.getId(),allOrgList));
            List<Org> childrens = czryDao.getCzryListByDeptId(org.getId());
            if (childrens!=null && childrens.size()>0){
                org.getChildren().addAll(childrens);
            }
        }
        // 排序
        Collections.sort(childList, order());
        // 如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Org>();
        }
        return childList;
    }
}
