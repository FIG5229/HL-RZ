package com.integration.service;

import com.integration.entity.CiKpiThres;
import com.integration.entity.CiKpiTypeInfo;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @version 1.0
 * @date 2018-12-11 05:47:33
 */

public interface CiKpiTypeService {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    public List<CiKpiTypeInfo> getAllPage(HashMap<Object, Object> params);

    /**
     * 查询总数
     *
     * @param params
     * @return
     */
    public int getAllCount(HashMap<Object, Object> params);

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    public List<CiKpiTypeInfo> getAllList(HashMap<Object, Object> params);

    /**
     * 查询单条
     *
     * @param params
     * @return
     */
    public CiKpiTypeInfo getInfo(HashMap<Object, Object> params);

    /**
     * 修改单条记录
     *
     * @param info
     */
    public int updateInfo(CiKpiTypeInfo info);

    /**
     * 新增单条记录
     *
     * @param info
     */
    public void insertInfo(CiKpiTypeInfo info);

    /**
     * 删除单条记录
     *
     * @param id
     */
    public void deleteInfo(String id);

    /**
     * 根据Id获取对象id列表值
     *
     * @param id
     * @return
     */
    List<String> findById(String id);

    /**
     * 根据id获取Thres列表
     *
     * @param id
     * @return
     */
    List<CiKpiThres> findByThres(String id);

	int getHangKpiByCiId(String ciTypeId);
}