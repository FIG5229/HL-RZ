package com.integration.service;

import com.integration.entity.Domain;
import com.integration.entity.Org;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.service
 * @ClassName: DomainService
 * @Author: ztl
 * @Description:1.数据域表用户控制数据权限。 2. 可按组织机构（单位）配置数据域，在业务表中存放数据域字段
 * @Date: 2020/5/15 8:55
 * @Version: 1.0
 * @Modified By:
 */
@Service
@Transactional
public interface DomainService {

    /**
     *根据机构代码查询对应数据域
     *
     * @param domainCode
     * @return
     */
    public Domain findDomainByDomainCode(String domainCode);

    /**
     * 保存数据域对象
     *
     * @param domain 数据域对象
     * @return
     */
    public int insertDomain(Domain domain);

    /**
     * 根据机构信息修改数据域
     *
     * @param org
     * @return
     */
    int updateDomainByOrg(Org org);

    /**
     * 根据机构代码删除数据域
     *
     * @param id
     * @return
     */
    int deleteByDomainCode(String id);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 根据机构代码获取对应的数据域
     */
    List<String> getRoleDomainList(List<String> dataDomainLists);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有数据域数据
     */
    List<Domain> getAllDomainData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 处理同步数据域
     */
    void handleDomainData(List<Domain> domainList);
    /**
     * @Author: ztl
     * date: 2021-08-25
     * @description: 获取所有数据域
     */
    List<String> findAllDomainId();
}
