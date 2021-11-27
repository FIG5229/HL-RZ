package com.integration.dao;

import com.integration.entity.Subsystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.integration.entity.Subsystem;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: SubsystemDao
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
@Mapper
public interface SubsystemDao {

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
    int deleteSubsystem(@Param("id") String id);

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


    String findKpiCode(@Param("kpi_name") String kpi_name);

    String findNameById(@Param("id") String id);

    /**
     * 根据id查询servername
     * @param id
     * @return
     */
    String findServerNameById(String id);

    int deleteUserByCzrySys(@Param("czryId") String czryId, @Param("subsystem") String subsystem);

	Map getUserInfoByLoginId(@Param("userId")String userId, @Param("sub") String sub);
}
