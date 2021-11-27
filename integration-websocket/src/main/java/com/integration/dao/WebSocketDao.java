package com.integration.dao;

import com.integration.entity.WebSocketEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: integration
 * @description
 * @author: hlq
 * @create: 2020-03-05 15:27
 **/

@Mapper
public interface WebSocketDao {
    List<Map> getUnSends();

    int romoveUnSends(@Param("listIds") List<String> listIds);

    int inssetUnSends(@Param("webSocketEntitys") List<WebSocketEntity> webSocketEntitys);

    void deleteByReceiverId(@Param("receiverId") List users);
}
