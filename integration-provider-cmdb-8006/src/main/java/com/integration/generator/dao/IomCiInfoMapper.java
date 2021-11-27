package com.integration.generator.dao;

import com.integration.generator.entity.IomCiInfo;
import com.integration.generator.entity.IomCiInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiInfoMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账信息管理
*/
public interface IomCiInfoMapper {
    long countByExample(IomCiInfoExample example);

    int deleteByExample(IomCiInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiInfo record);

    int insertSelective(IomCiInfo record);

    List<IomCiInfo> selectByExample(IomCiInfoExample example);

    IomCiInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiInfo record, @Param("example") IomCiInfoExample example);

    int updateByExample(@Param("record") IomCiInfo record, @Param("example") IomCiInfoExample example);

    int updateByPrimaryKeySelective(IomCiInfo record);

    int updateByPrimaryKey(IomCiInfo record);
}