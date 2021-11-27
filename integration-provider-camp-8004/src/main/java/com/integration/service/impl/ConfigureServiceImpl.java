package com.integration.service.impl;

import com.integration.dao.ConfigureDao;
import com.integration.entity.ConfMapping;
import com.integration.entity.ConfOutMapping;
import com.integration.entity.Configure;
import com.integration.service.ConfigureService;
import com.integration.utils.SeqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service.impl
* @ClassName: ConfigureServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 配置
*/
@Service
@Transactional
public class ConfigureServiceImpl implements ConfigureService {

    @Resource
    private ConfigureDao configureDao;

    @Override
    public List<Configure> findAllC() {
        return configureDao.findAllC();
    }

    /**
     * 根据子系统id查询所有配置
     * @param sid
     * @return
     */
    @Override
    public List<Configure> findBySid(String sid) {
        return configureDao.findBySid(sid);
    }

    /**
     * 添加配置
     * @param configure
     * @return
     */
    @Override
    public int addConfigure(Configure configure) {
        configure.setId(SeqUtil.nextId()+"");
        return configureDao.addConfigure(configure);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public int deleteConfById(String id) {
        //根据配置id删除出参和入参
        int i = configureDao.deleteMappingByConfId(id);
        int i1 = configureDao.deleteOutMappingByConfId(id);
        //删除配置
        return configureDao.deleteConfById(id);
    }

    /**
     * 根据id修改
     * @param configure
     * @return
     */
    @Override
    public int updateConfById(Configure configure) {
        return configureDao.updateConfById(configure);
    }

    /**
     * 根据配置id查询入参
     * @param configId
     * @return
     */
    @Override
    public List<ConfMapping> findMappingByConfId(String configId) {
        return configureDao.findMappingByConfId(configId);
    }

    /**
     * 根据配置id查询出参
     * @param configId
     * @return
     */
    @Override
    public List<ConfOutMapping> findOutMappingByConfId(String configId) {
        return configureDao.findOutMappingByConfId(configId);
    }

    /**
     * 添加入参
     * @param confMapping
     * @return
     */
    @Override
    public int addMapping(ConfMapping confMapping) {
        confMapping.setId(SeqUtil.nextId()+"");
        return configureDao.addMapping(confMapping);
    }

    /**
     * 根据id删除入参
     * @param id
     * @return
     */
    @Override
    public int deleteMappingById(String id) {
        return configureDao.deleteMappingById(id);
    }

    /**
     * 修改入参
     * @param confMapping
     * @return
     */
    @Override
    public int updateMapping(ConfMapping confMapping) {
        return configureDao.updateMapping(confMapping);
    }

    /**
     * 添加设备
     * @param confOutMapping
     * @return
     */
    @Override
    public int addOutMapping(ConfOutMapping confOutMapping) {
        confOutMapping.setId(SeqUtil.nextId()+"");
        return configureDao.addOutMapping(confOutMapping);
    }

    /**
     * 修改出参
     * @param confOutMapping
     * @return
     */
    @Override
    public int updateOutMapping(ConfOutMapping confOutMapping) {
        return configureDao.updateOutMapping(confOutMapping);
    }

    /**
     * 删除出参
     * @param id
     * @return
     */
    @Override
    public int deleteOutMappingById(String id) {
        return configureDao.deleteOutMappingById(id);
    }

    @Override
    public String findSubsystemByUrl(String url) {
        return configureDao.findSubsystemByUrl(url);
    }

    @Override
    public List<Map> findAllCM() {
        return configureDao.findAllCM();
    }

    @Override
    public List<Map> findAllCOM() {
        return configureDao.findAllCOM();
    }
}
