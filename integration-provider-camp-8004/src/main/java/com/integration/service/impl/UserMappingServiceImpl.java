package com.integration.service.impl;

import com.integration.dao.UserMappingDao;
import com.integration.entity.UserMapping;
import com.integration.service.UserMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service.impl
* @ClassName: UserMappingServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 用户映射
*/
@Transactional
@Service
public class UserMappingServiceImpl implements UserMappingService {

    @Resource
    private UserMappingDao mappingDao;

    /**
     * 新增
     * @param userMapping
     * @return
     */
    @Override
    public int addUserM(UserMapping userMapping) {
        return mappingDao.addUserM(userMapping);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int deleteUserM(String id) {
        return mappingDao.deleteUserM(id);
    }

    /**
     * 修改
     * @param userMapping
     * @return
     */
    @Override
    public int updateUserM(UserMapping userMapping) {
        return mappingDao.updateUserM(userMapping);
    }

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<UserMapping> findAllM(String czry_id) {
        return mappingDao.findAllM(czry_id);
    }

    /**
     * 根据id查询密码
     * @param czry_id
     * @return
     */
    @Override
    public Map findPassWordByID(String czry_id,String subsystem) {
        return mappingDao.findPassWordByID(czry_id,subsystem);
    }

    /**
     * 查询所有用户名
     * @return
     */
    @Override
    public String findAllDldm(String czry_id, String subsystem) {
        return mappingDao.findAllDldm(czry_id, subsystem);
    }

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    @Override
    public int updatePassword(String password, String id) {
        return mappingDao.updatePassword(password,id);
    }

    @Override
    public String findPassWord(String id) {
        return mappingDao.findPassWord(id);
    }

    @Override
    public List<String> findSystemByCzry(String czry_id) {
        return mappingDao.findSystemByCzry(czry_id);
    }

    @Override
    public String findPasswordByCzry(String czry_id, String subsystem) {
        return mappingDao.findPasswordByCzry(czry_id,subsystem);
    }

    @Override
    public UserMapping findById(String id) {
        return mappingDao.findById(id);
    }
}
