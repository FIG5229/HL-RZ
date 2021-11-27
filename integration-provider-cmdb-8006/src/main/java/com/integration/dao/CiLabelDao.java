package com.integration.dao;

import com.integration.entity.CiLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: CiLabelDao
 * @Author: ztl
 * @Date: 2020-12-03
 * @Version: 1.0
 * @description:CI标签
 */
@Mapper
@Repository
public interface CiLabelDao {
    List<Map<String, String>> getLabelListByDirId(@Param("dirId") String dirId,@Param("domainId") String domainId,@Param("labelName") String labelName);

    Map<String, Object> getLabelById(@Param("labelId") String labelId,@Param("domainId") String domainId);

    boolean updateLabel(CiLabel ciLabel);

    boolean addLabel(CiLabel ciLabel);

    boolean deleteLabelById(String id);

    int labelNameExists(@Param("labelName") String labelName,@Param("id") String id,@Param("domainId") String domainId);
}
