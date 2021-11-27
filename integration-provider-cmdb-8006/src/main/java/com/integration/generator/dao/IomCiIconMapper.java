package com.integration.generator.dao;

import com.integration.generator.entity.IomCiIcon;
import com.integration.generator.entity.IomCiIconExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiIconMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 图标管理
*/
public interface IomCiIconMapper {
    long countByExample(IomCiIconExample example);

    int deleteByExample(IomCiIconExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiIcon record);

    int insertSelective(IomCiIcon record);

    List<IomCiIcon> selectByExample(IomCiIconExample example);

    IomCiIcon selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiIcon record, @Param("example") IomCiIconExample example);

    int updateByExample(@Param("record") IomCiIcon record, @Param("example") IomCiIconExample example);

    int updateByPrimaryKeySelective(IomCiIcon record);

    int updateByPrimaryKey(IomCiIcon record);
}