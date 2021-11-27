package com.integration.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.integration.entity.Face;
/**
* @Package: com.integration.dao
* @ClassName: FaceDao
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 人像比对
*/
@Mapper
public interface FaceDao {
    int deleteByPrimaryKey(String id);

    int insert(Face record);

    int insertSelective(Face record);

    Face selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Face record);

    int updateByPrimaryKey(Face record);
    
    public List<Face> findFaceList();
}