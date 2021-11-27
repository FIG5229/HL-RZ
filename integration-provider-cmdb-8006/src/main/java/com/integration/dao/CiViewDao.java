package com.integration.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.integration.entity.vo.CiView;

/**
* @Package: com.integration.dao
* @ClassName: CiViewDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface CiViewDao {
    List<CiView> selectList(@Param("ciId")String ciId,@Param("ciIds")String ciIds,@Param("ciTypeId")String ciTypeId,@Param("ciCode")String ciCode,@Param("sourceId")String sourceId,@Param("major")String major,@Param("jsondata")Map<String, Object> jsondata);

    List<CiView> findCi(@Param("mapCList") List<Map<String, Object>> mapCList, @Param("mapJList") List<Map<String, Object>> mapJList);
    
    List<CiView> selectListByJniom(@Param("ciId")String ciId, @Param("ciIds")String ciIds, @Param("ciTypeId")String ciTypeId, @Param("ciTypeName")String ciTypeName, @Param("ciCode")String ciCode, @Param("sourceId")String sourceId, @Param("major")String major, @Param("param")Map<String,Object> param);
}