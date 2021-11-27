package com.integration.service.impl;

import com.integration.controller.MenuConntroller;
import com.integration.dao.DomainDao;
import com.integration.entity.Domain;
import com.integration.entity.Org;
import com.integration.entity.PageResult;
import com.integration.mybatis.utils.dbInit.DbInitialization;
import com.integration.rabbit.Sender;
import com.integration.service.DomainService;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.service.impl
 * @ClassName: DomainServiceImpl
 * @Author: ztl
 * @Description:数据域表用户控制数据权限
 * @Date: 2020/5/15 9:00
 * @Version: 1.0
 * @Modified By:
 */
@Service
public class DomainServiceImpl implements DomainService {

    private final static Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);
    @Resource
    DomainDao domainDao;

    @Autowired
    private Sender sender;

    @Resource
    DbInitialization dbInit;
    /**
     *根据机构代码查询对应数据域
     * @param domainCode 机构代码
     * @return
     */
    @Override
    public Domain findDomainByDomainCode(String domainCode) {
        return domainDao.findDomainByDomainCode(domainCode);
    }

    /**
     * 新增数据域
     * @param domain 数据域对象
     * @return
     */
    @Override
    public int insertDomain(Domain domain) {
        int result = domainDao.insertDomain(domain);
        try {
            sender.sendDomainAndOrgData();
        }catch (Exception e){
            logger.error("同步数据域及组织机构数据异常",e);
        }
        return result;
    }
    /**
     * 根据机构信息修改数据域
     *
     * @param org
     * @return
     */
    @Override
    public int updateDomainByOrg(Org org) {

        int count = domainDao.getDomainCount(org.getId());
        if (count>0){
            if ("sysadmin".equals(TokenUtils.getTokenDldm()) && org.getDataDomain()!=null && !"".equals(org.getDataDomain())){
                Domain domain = new Domain();
                domain.setDomain_code(org.getId());
                domain.setDomain_name(org.getName());
                domain.setDomain_id(org.getDataDomain());
                int result = domainDao.updateDomain(domain);
                try {
                    sender.sendDomainAndOrgData();
                }catch (Exception e){
                    logger.error("同步数据域及组织机构数据异常",e);
                }
                try{
                    dbInit.initDomain(org.getDataDomain());
                }catch (Exception e){
                    logger.error("初始化数据域失败",e);
                }
                return result;
            }

        }else{
            /**
             * 如果存在数据域则进行数据域表数据存储
             */
            if (org.getDataDomain()!=null && !"".equals(org.getDataDomain())){
                //存在数据域则进行数据域的存储
                Domain domain = new Domain();
                domain.setId(SeqUtil.getId());
                domain.setYxbz("1");
                domain.setDomain_code(org.getId());
                domain.setDomain_name(org.getName());
                domain.setDomain_id(org.getDataDomain());
                int result = domainDao.insertDomain(domain);
                try {
                    sender.sendDomainAndOrgData();
                }catch (Exception e){
                    logger.error("同步数据域及组织机构数据异常",e);
                }
                try{
                    dbInit.initDomain(org.getDataDomain());
                }catch (Exception e){
                    logger.error("初始化数据域失败",e);
                }
                return result;
            }
        }
        return 0;
    }
    /**
     * 根据机构代码删除数据域
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByDomainCode(String id) {
        int result = domainDao.deleteByDomainCode(id);
        try {
            sender.sendDomainAndOrgData();
        }catch (Exception e){
            logger.error("同步数据域及组织机构数据异常",e);
        }
        return result;
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 根据机构代码获取对应的数据域
     */
    @Override
    public List<String> getRoleDomainList(List<String> dataDomainLists) {
        return domainDao.getRoleDomainList(dataDomainLists);
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有数据域数据
     */
    @Override
    public List<Domain> getAllDomainData() {
        return domainDao.getAllDomainData();
    }
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 处理同步数据域
     */
    @Override
    public void handleDomainData(List<Domain> domainList) {
        domainDao.deleteAllDomainData();
        domainDao.saveAllDomainData(domainList);
    }
    /**
     * @Author: ztl
     * date: 2021-08-25
     * @description: 获取所有数据域
     */
    @Override
    public List<String> findAllDomainId() {
        return domainDao.findAllDomainId();
    }
}
