package com.integration.service.impl;

import com.integration.dao.OrgDao;
import com.integration.dao.RoleDomainDao;
import com.integration.entity.Org;
import com.integration.entity.RoleDomain;
import com.integration.service.RoleDomainService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: RoleDomainServiceImpl
 * @Author: ztl
 * @Date: 2021-07-20
 * @Version: 1.0
 * @description:角色对应数据域关系
 */
@Service
public class RoleDomainServiceImpl implements RoleDomainService {

    @Resource
    private RoleDomainDao roleDomainDao;

    @Autowired
    private OrgDao orgDao;

    /**
     * 根据角色代码获取角色对应的数据域
     *
     * @param roleDm
     *
     * @return
     */
    @Override
    public RoleDomain getRoleDomainByRoleDm(String roleDm) {
        return roleDomainDao.getRoleDomainByRoleDm(roleDm);
    }

    /**
     * 保存角色对应的数据域
     *
     * @param roleDomain
     * @return
     */
    @Override
    public boolean saveRoleDomain(RoleDomain roleDomain) {
        if (roleDomain.getId()!=null && !"".equals(roleDomain.getId())){
            roleDomain.setXgrId(TokenUtils.getTokenUserId());
            if (!"".equals(roleDomain.getDataDomain()) &&roleDomain.getDataDomain()!=null){
                return roleDomainDao.updateRoleDomain(roleDomain);
            }else{
                return roleDomainDao.deleteRoleDomain(roleDomain.getId());
            }
        }else{
            roleDomain.setId(SeqUtil.getId());
            roleDomain.setCjrId(TokenUtils.getTokenUserId());
            return roleDomainDao.addRoleDomain(roleDomain);
        }
    }

    @Override
    public List<Org> findRoleDomainOrgList(String id) {
        //所有组织机构列表
        List<Org> orgList = orgDao.findRoleDomainOrgList(id);
        if (orgList!=null && orgList.size()>0){
            orgList = orgList.stream().filter(x->x.getDataDomain()!=null).collect(Collectors.toList());
        }
        //根节点
        List<Org> rootOrg = new ArrayList<>();
        //将当前登录用户的机构ID作为根节点
        if (StringUtils.isNotEmpty(id)) {
            Org org = orgDao.findRoleDomainOrg(id);
            if (org!=null && org.getDataDomain()!=null){
                rootOrg.add(org);
            }
        } else {
            //获取所有根节点pid 为0的节点
            List<Org> rootOrgs = orgDao.findRoleDomainAllMax();
            if (rootOrgs!=null && rootOrgs.size()>0){
                rootOrg = rootOrgs.stream().filter(x->x.getDataDomain()!=null).collect(Collectors.toList());
            }
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
}
