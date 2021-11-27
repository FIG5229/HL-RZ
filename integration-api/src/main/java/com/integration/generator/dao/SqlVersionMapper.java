package com.integration.generator.dao;

import com.integration.generator.entity.SqlVersion;
import com.integration.generator.entity.SqlVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SqlVersionMapper {
    long countByExample(SqlVersionExample example);

    int deleteByExample(SqlVersionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SqlVersion record);

    int insertSelective(SqlVersion record);

    List<SqlVersion> selectByExample(SqlVersionExample example);

    SqlVersion selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SqlVersion record, @Param("example") SqlVersionExample example);

    int updateByExample(@Param("record") SqlVersion record, @Param("example") SqlVersionExample example);

    int updateByPrimaryKeySelective(SqlVersion record);

    int updateByPrimaryKey(SqlVersion record);
    /**
     * @Author: ztl
     * date: 2021-09-08
     * @description: 校验当前数据域是否初始化相应数据
     */
    boolean checkDomainId(@Param("domainId") String domainId);

    void insertDomain(@Param("id") String id,@Param("domainId") String domianId);
}