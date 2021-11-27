package com.integration.dao;

import com.integration.entity.SgccUser;

import java.util.List;

/**
 * @author zf
 */
public interface SgccUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(SgccUser record);

    int insertSelective(SgccUser record);

    SgccUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(SgccUser record);

    int updateByPrimaryKey(SgccUser record);

    List<SgccUser> selectList(SgccUser record);

}