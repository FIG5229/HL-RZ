package com.integration.generator.dao;

import com.integration.generator.entity.IomCiTypeItem;
import com.integration.generator.entity.IomCiTypeItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiTypeItemMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类定义管理
*/
public interface IomCiTypeItemMapper {
    long countByExample(IomCiTypeItemExample example);

    int deleteByExample(IomCiTypeItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiTypeItem record);

    int insertSelective(IomCiTypeItem record);

    List<IomCiTypeItem> selectByExample(IomCiTypeItemExample example);

    IomCiTypeItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiTypeItem record, @Param("example") IomCiTypeItemExample example);

    int updateByExample(@Param("record") IomCiTypeItem record, @Param("example") IomCiTypeItemExample example);

    int updateByPrimaryKeySelective(IomCiTypeItem record);

    int updateByPrimaryKey(IomCiTypeItem record);
}