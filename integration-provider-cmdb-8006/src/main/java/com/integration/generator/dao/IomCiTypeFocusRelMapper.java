package com.integration.generator.dao;

import com.integration.generator.entity.IomCiTypeFocusRel;
import com.integration.generator.entity.IomCiTypeFocusRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiTypeFocusRelMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiTypeFocusRelMapper {
    long countByExample(IomCiTypeFocusRelExample example);

    int deleteByExample(IomCiTypeFocusRelExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiTypeFocusRel record);

    int insertSelective(IomCiTypeFocusRel record);

    List<IomCiTypeFocusRel> selectByExample(IomCiTypeFocusRelExample example);

    IomCiTypeFocusRel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiTypeFocusRel record, @Param("example") IomCiTypeFocusRelExample example);

    int updateByExample(@Param("record") IomCiTypeFocusRel record, @Param("example") IomCiTypeFocusRelExample example);

    int updateByPrimaryKeySelective(IomCiTypeFocusRel record);

    int updateByPrimaryKey(IomCiTypeFocusRel record);
}