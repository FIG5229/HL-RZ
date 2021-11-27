package com.integration.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: integration
 * @Package: com.integration.dao
 * @ClassName: UnsendNewsDao
 * @Author: ztl
 * @Date: 2021-05-06
 * @Version: 1.0
 * @description:未发送交班记录信息
 */
@Mapper
public interface UnsendNewsDao {

    List<Map> getUnSendNews();

    int deleteAllUnSendNews();

    int deleteUnSendNews(@Param("listIds") List<String> listIds);
}
