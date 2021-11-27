package com.integration.dao;

import com.integration.entity.IomCampJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
* @Package: com.integration.dao
* @ClassName: IomCampJobMapper
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 定时任务
*/
@Mapper
public interface IomCampJobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IomCampJob record);

    int insertSelective(IomCampJob record);

    IomCampJob selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IomCampJob record);

    int updateByPrimaryKey(IomCampJob record);

    List<IomCampJob> selectList(IomCampJob record);
}