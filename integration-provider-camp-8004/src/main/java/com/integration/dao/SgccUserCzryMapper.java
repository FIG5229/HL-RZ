package com.integration.dao;

import com.integration.entity.SgccUserCzry;

import java.util.List;


/**
 * @author zf
 */
public interface SgccUserCzryMapper {
    int insert(SgccUserCzry record);

    int insertSelective(SgccUserCzry record);

    int deleteByParam(SgccUserCzry record);

    List<SgccUserCzry> selectList(SgccUserCzry record);
}