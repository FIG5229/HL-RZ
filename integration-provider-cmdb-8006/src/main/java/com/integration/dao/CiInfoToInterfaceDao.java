package com.integration.dao;

import com.integration.entity.CiVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: CiInfoToInterfaceDao
 * @Author: ztl
 * @Date: 2020-09-11
 * @Version: 1.0
 * @description:为接口平台提供CI数据
 */
@Mapper
@Repository
public interface CiInfoToInterfaceDao {

    /**
     * 查询CI大类及对应的关联字段
     * @return
     */
    public List<Map<String,String>> findCiType();

    /**
     * 查询接口平台所需CI数据
     * @param ciTypeId
     * @param mpCiItem
     * @return
     */
    List<Map<String, Object>> findCiInfo(@Param("ciTypeId") String ciTypeId,@Param("mpCiItem") String mpCiItem);

    /**
     * 查询是否有CI版本数据
     * @return
     */
    int findCiVersionCount();

    /**
     * 更新CI版本信息
     * @param ciVersion
     * @return
     */
    boolean updateCiVersion(CiVersion ciVersion);

    /**
     * 新增版本信息
     * @param ciVersion
     * @return
     */
    boolean insertCiVersion(CiVersion ciVersion);

    /**
     * 查询最新CI版本信息
     * @return
     */
    String findCiVersion();
}
