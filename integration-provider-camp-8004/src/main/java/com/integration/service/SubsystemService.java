package com.integration.service;

import com.integration.entity.Subsystem;

import java.util.List;
import java.util.Map;

import com.integration.entity.Subsystem;
/**
* @Package: com.integration.service
* @ClassName: SubsystemService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 子系统
*/
public interface SubsystemService {

    /**
     * 查询所有子系统
     * @return
     */
    List<Subsystem> findAllS();

    /**
     * 添加子系统
     * @param subsystem
     * @return
     */
    int addSubsystem(Subsystem subsystem);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteSubsystem(String czryId, String id);

    /**
     * 修改
     * @param subsystem
     * @return
     */
    int updateSubsystem(Subsystem subsystem);

    /**
     * 查询所有名称
     * @return
     */
    List<String> findAllName();

    String findKpiCode(String kpi_name);

    String findNameById(String id);

	Map getUserInfoByLoginId(String userId, String sub);

}
