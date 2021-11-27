package com.integration.generator.dao;

import com.integration.generator.entity.IomCiMgtLog;
import com.integration.generator.entity.IomCiMgtLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiMgtLogMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiMgtLogMapper {
    long countByExample(IomCiMgtLogExample example);

    int deleteByExample(IomCiMgtLogExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiMgtLog record);

    int insertSelective(IomCiMgtLog record);

    List<IomCiMgtLog> selectByExample(IomCiMgtLogExample example);

    IomCiMgtLog selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiMgtLog record, @Param("example") IomCiMgtLogExample example);

    int updateByExample(@Param("record") IomCiMgtLog record, @Param("example") IomCiMgtLogExample example);

    int updateByPrimaryKeySelective(IomCiMgtLog record);

    int updateByPrimaryKey(IomCiMgtLog record);
}