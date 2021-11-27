package com.integration.service;

import com.integration.entity.UserMapping;

import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service
* @ClassName: UserMappingService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 用户映射
*/
public interface UserMappingService {

    /**
     * 新增
     * @param userMapping
     * @return
     */
    int addUserM(UserMapping userMapping);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteUserM( String id);

    /**
     * 修改
     * @param userMapping
     * @return
     */
    int updateUserM(UserMapping userMapping);

    /**
     * 全部查询
     * @return
     */
    List<UserMapping> findAllM(String czry_id);

    /**
     * 根据id查询密码
     * @param czry_id
     * @return
     */
    Map findPassWordByID(String czry_id,String subsystem);

    /**
     * 查询所有的用户名
     * @return
     */
    String findAllDldm(String czry_id, String subsystem);

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    int updatePassword(String password, String id);

    /**
     * 根据id查询密码
     * @param id
     * @return
     */
    String findPassWord(String id);

    /**
     * 通过用户id查询子系统
     * @param czry_id
     * @return
     */
    List<String> findSystemByCzry(String czry_id);


    /**
     * 通过用户id和子系统查询密码
     * @param czry_id
     * @param subsystem
     * @return
     */
    String findPasswordByCzry( String czry_id, String subsystem);

    UserMapping findById(String id);
}
