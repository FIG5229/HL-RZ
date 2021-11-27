package com.integration.generator.dao;

import com.integration.generator.entity.IomCiRltNodeCdt;
import com.integration.generator.entity.IomCiRltNodeCdtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
* @Package: com.integration.generator.dao
* @ClassName: IomCiRltNodeCdtMapper
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:
*/
public interface IomCiRltNodeCdtMapper {
    long countByExample(IomCiRltNodeCdtExample example);

    int deleteByExample(IomCiRltNodeCdtExample example);

    int deleteByPrimaryKey(String id);

    int insert(IomCiRltNodeCdt record);

    int insertSelective(IomCiRltNodeCdt record);

    List<IomCiRltNodeCdt> selectByExample(IomCiRltNodeCdtExample example);

    IomCiRltNodeCdt selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IomCiRltNodeCdt record, @Param("example") IomCiRltNodeCdtExample example);

    int updateByExample(@Param("record") IomCiRltNodeCdt record, @Param("example") IomCiRltNodeCdtExample example);

    int updateByPrimaryKeySelective(IomCiRltNodeCdt record);

    int updateByPrimaryKey(IomCiRltNodeCdt record);
}