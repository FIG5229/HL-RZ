package com.integration.service;

import com.integration.entity.IomCiKpiClass;

import java.util.List;
import java.util.Map;

/**
 * 指标大类表(IomCiKpiClass)表服务接口
 *
 * @author makejava
 * @since 2019-11-01 11:37:02
 */
public interface IomCiKpiClassService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IomCiKpiClass queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<IomCiKpiClass> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param iomCiKpiClas 实例对象
     * @return 实例对象
     */
    int insert(IomCiKpiClass iomCiKpiClas);

    /**
     * 修改数据
     *
     * @param iomCiKpiClas 实例对象
     * @return 实例对象
     */
    IomCiKpiClass update(IomCiKpiClass iomCiKpiClas);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 查询所有的数据
     *
     * @return
     */
    List<Map> selectAll();

    /**
     * 查询名称是否存在
     *
     * @param name
     * @return
     */
    int booById(String name);

    List<IomCiKpiClass> getAllIomCiKpiClas();

    public List<Map<String, Object>> getCiKpiAllByPmv();

    /**
     * 通过name查找大类是否存在
     *
     * @param name
     * @return
     */
    IomCiKpiClass findByName(String name);
}