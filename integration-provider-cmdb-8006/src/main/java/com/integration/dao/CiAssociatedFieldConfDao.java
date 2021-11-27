package com.integration.dao;

import com.integration.entity.CiAssociatedFieldConf;
import com.integration.entity.CiAssociatedTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: CiAssociatedFieldConfDao
 * @Author: ztl
 * @Date: 2021-07-07
 * @Version: 1.0
 * @description:关联字段配置数据库接口
 */
@Mapper
@Repository
public interface CiAssociatedFieldConfDao {


    boolean updateFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf);

    boolean addFieldConf(CiAssociatedFieldConf ciAssociatedFieldConf);

    boolean deleteFieldConf(@Param("id") String id,@Param("xgrId") String xgrId);

    List<CiAssociatedFieldConf> findFieldConfList(@Param("sourceCiId") String sourceCiId,@Param("ciTypeId") String ciTypeId,@Param("domainId") String domainId);

    CiAssociatedFieldConf findFieldConf(@Param("id") String id);

    List<CiAssociatedFieldConf> findConfList(@Param("sourceCiIdList") List<String> sourceCiIdList,@Param("ciTypeIdList") List<String> ciTypeIdList);

    List<String> findSourceCiIdByCiTypeId(@Param("ciTypeId") String ciTypeId);

    void addconfTrigger(CiAssociatedTrigger ciAssociatedTrigger);
}
