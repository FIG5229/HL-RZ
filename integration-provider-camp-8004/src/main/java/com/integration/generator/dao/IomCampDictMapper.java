package com.integration.generator.dao;

import com.integration.entity.Dict;
import com.integration.generator.entity.IomCampDict;
import com.integration.generator.entity.IomCampDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCampDictMapper
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 字典管理
*/
public interface IomCampDictMapper {
    long countByExample(IomCampDictExample example);

    int deleteByExample(IomCampDictExample example);

    int deleteByPrimaryKey(String dictId);

    int insert(IomCampDict record);

    int insertSelective(IomCampDict record);

    List<IomCampDict> selectByExample(IomCampDictExample example);

    IomCampDict selectByPrimaryKey(String dictId);

    int updateByExampleSelective(@Param("record") IomCampDict record, @Param("example") IomCampDictExample example);

    int updateByExample(@Param("record") IomCampDict record, @Param("example") IomCampDictExample example);

    int updateByPrimaryKeySelective(IomCampDict record);

    int updateByPrimaryKey(IomCampDict record);
    /**
     * @Author: ztl
     * date: 2021-08-11
     * @description: 根据上级ID和dictBm获取单条字典数据
     */
    List<Dict> getDictBySjIdDictBm(@Param("sjId") String sjId, @Param("dictBm") String dictBm);
}