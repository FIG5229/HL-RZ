package com.integration.dao;

import com.integration.entity.Domain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: DomainDao
 * @Author: ztl
 * @Description:1.数据域表用户控制数据权限。 2. 可按组织机构（单位）配置数据域，在业务表中存放数据域字段
 * @Date: 2020/5/15 8:32
 * @Version: 1.0
 * @Modified By:
 */
@Mapper
public interface DomainDao {

    /**
     * 根据机构代码查询对应数据域
     * @param domainCode
     * @return
     */
    public Domain findDomainByDomainCode(String domainCode);

    /**
     *新增数据域
     *
     * @param domain 数据域对象
     * @return
     */
    public int insertDomain(Domain domain);

    /**
     * 根据机构ID获取数据域数量
     *
     * @param id
     * @return
     */
    int getDomainCount(String id);

    /**
     * 修改机构对应数据域
     *
     * @param domain
     * @return
     */
    int updateDomain(Domain domain);
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
    List<String> getRoleDomainList(@Param("list") List<String> dataDomainLists);
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 获取所有数据域数据
     */
    List<Domain> getAllDomainData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 删除所有数据域数据
     */
    void deleteAllDomainData();
    /**
     * @Author: ztl
     * date: 2021-08-17
     * @description: 批量增加数据域
     */
    void saveAllDomainData(@Param("list") List<Domain> domainList);
    /**
     * @Author: ztl
     * date: 2021-08-25
     * @description: 获取所有数据域
     */
    List<String> findAllDomainId();
}
