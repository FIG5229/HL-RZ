package com.integration.service;

import com.integration.entity.ConfMapping;
import com.integration.entity.ConfOutMapping;
import com.integration.entity.Configure;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.service
* @ClassName: ConfigureService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 管理
*/
public interface ConfigureService {

    /**
     * 查询所有
     * @return
     */
    List<Configure> findAllC();

    /**
     * 根据子系统id查询配置
     * @param sid
     * @return
     */
    List<Configure> findBySid(String sid);

    /**
     * 添加配置
     * @param configure
     * @return
     */
    int addConfigure(Configure configure);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteConfById(String id);

    /**
     * 根据id修改
     * @param configure
     * @return
     */
    int updateConfById(Configure configure);


    /**
     * 根据配置id查询入参
     * @param configId
     * @return
     */
    List<ConfMapping> findMappingByConfId(String configId);

    /**
     * 根据配置id查询出参
     * @param configId
     * @return
     */
    List<ConfOutMapping> findOutMappingByConfId(String configId);

    /**
     * 添加入参
     * @param confMapping
     * @return
     */
    int addMapping(ConfMapping confMapping);

    /**
     * 根据id删除入参
     * @param id
     * @return
     */
    int deleteMappingById(String id);

    /**
     * 修改入参
     * @param confMapping
     * @return
     */
    int updateMapping(ConfMapping confMapping);

    /**
     * 添加出参
     * @param confOutMapping
     * @return
     */
    int addOutMapping(ConfOutMapping confOutMapping);

    /**
     * 修改出参
     * @param confOutMapping
     * @return
     */
    int updateOutMapping(ConfOutMapping confOutMapping);

    /**
     * 根据id删除出参
     * @param id
     * @return
     */
    int deleteOutMappingById(String id);

    /**
     * 根据url查询子系统
     * @param url
     * @return
     */
    String findSubsystemByUrl(String url);

    /**

     * 查询入参数据
     * @return
     */
    List<Map> findAllCM();

    /**
     * 查询出参数据
     * @return
     */
    List<Map> findAllCOM();
}
