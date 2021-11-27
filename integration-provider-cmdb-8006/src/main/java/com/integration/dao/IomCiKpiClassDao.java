package com.integration.dao;

import com.integration.entity.IomCiKpiClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 指标大类表(IomCiKpiClass)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-18 11:25:43
 */
@Mapper
@Repository
public interface IomCiKpiClassDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IomCiKpiClass queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<IomCiKpiClass> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param iomCiKpiClass 实例对象
     * @return 对象列表
     */
    List<IomCiKpiClass> queryAll(IomCiKpiClass iomCiKpiClass);

    /**
     * 新增数据
     *
     * @param iomCiKpiClass 实例对象
     * @return 影响行数
     */
    int insert(IomCiKpiClass iomCiKpiClass);

    /**
     * 修改数据
     *
     * @param iomCiKpiClass 实例对象
     * @return 影响行数
     */
    int update(IomCiKpiClass iomCiKpiClass);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);
    /**
     * 查询所有的数据
     * @return
     */
    List<Map> selectAll(@Param("domainId") String domainId);

    /**
     * 查询名称是否存在
     *
     * @param name
     * @return
     */
    int booById(String name);

    public List<IomCiKpiClass> selectAllByPmv();

    public List<Map<String, Object>> getCiKpiAllByPmv();
    
    public List<Map<String, Object>> getCiKpiClassInfoByKpiClassIds(Map<String,Object> itemMap);

    /**
     * 通过name查找大类是否存在
     *
     * @param name
     * @return
     */
    IomCiKpiClass findByName(@Param("name") String name);

}