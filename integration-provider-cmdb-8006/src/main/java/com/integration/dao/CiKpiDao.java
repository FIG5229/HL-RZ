

package com.integration.dao;

import com.integration.entity.CiKpiInfo;
import com.integration.entity.Kpi_Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */
@Mapper
@Repository
public interface CiKpiDao {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    public List<CiKpiInfo> getAllPage(HashMap<String, String> params);

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
    public List<CiKpiInfo> getAllList(HashMap<Object, Object> params);

    /**
     * 查询单条
     *
     * @param kpiId
     * @return
     */
    public CiKpiInfo getInfo(String kpiId);

    /**
     * 修改单条记录
     *
     * @param info
     */
    public int updateInfo(CiKpiInfo info);

    /**
     * 新增单条记录
     *
     * @param info
     */
    public int insertInfo(CiKpiInfo info);
    
    public int getIomCiKpiTypeByKpiIdAndCiTypeId(@Param("kpiId") String kpiId,@Param("ciTypeId") String ciTypeId);
    
    public List<Map<String,Object>> getIomCiKpiTypeInfos();
    
    public List<String> getObjIdByKpiId(@Param("kpiId") String kpiId);

    /**
     * 根据list，查询KpiType
     * @param kpiNames
     * @return
     */
    List<Kpi_Type> getKpiTypes(@Param("kpiNames") List kpiNames);

    /**
     * 根据list，删除kpiType
     */
    void deleteByKpiNames(@Param("newList") List<Kpi_Type> newList);


    /**
     * 批量添加
     */
    void insertByKpiNames(@Param("newList") List<Kpi_Type> newList);

    /**
     * 删除单条记录
     *
     * @param id
     */
    public int deleteInfo(String id);

    /**
     * 获取所有模型
     *
     * @return
     */
    public List<CiKpiInfo> getAllInfo(@Param("search") String search, @Param("domainId") String domainId);

    /**
     * 获取所有模型
     *
     * @return
     */
    public List<CiKpiInfo> getAllInfo(@Param("search") String search, @Param("isMatch") String isMatch, @Param("domainId") String domainId);

    /**
     * 获取组合实体
     *
     * @return
     */
    public List<Kpi_Type> getKpi_Type();

    /**
     * 根据ci大类id查询kpi
     *
     * @param ciTypeId
     * @return
     */
    public List<CiKpiInfo> selectKpiByCiTypeId(@Param("ciTypeId") String ciTypeId);
    
    /**
     * 根据ci大类id查询kpi 增加排序
     *
     * @param ciTypeId
     * @return
     */
    public List<CiKpiInfo> selectKpiByCiTypeIdSort(@Param("ciTypeId") String ciTypeId);
    
    public List<Map<String,Object>> selectKpiByCiTypeIdByPerformanceChart(@Param("ciTypeId") String ciTypeId,@Param("domainId") String domainId);
    
    /**
     * 根据ci大类id查询kpi
     *
     * @param ciTypeId
     * @return
     */
    public List<CiKpiInfo> selectKpiByCiTypeIdByPaging(@Param("ciTypeId") String ciTypeId,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);
    
    public List<Map<String,Object>> selectKpiByCiTypeIdByPagingNew(@Param("ciTypeId") String ciTypeId,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize,@Param("domainId")String domainId,@Param("kpiName")String kpiName);
    
    public int selectKpiByCiTypeIdByPagingCount(@Param("ciTypeId") String ciTypeId,@Param("domainId")String domainId,@Param("kpiName")String kpiName);
    /**
     * 根据name获取KPIID
     *
     * @param name
     * @return
     */
    CiKpiInfo findByNameKPI(@Param("name") String name);

    /**
     * 根据name获取KPI和大类信息
     *
     * @param name
     * @return
     */
    List<Map> findByNameKPIClass(@Param("name") String name);

    /**
     * 根据name获取KPI和大类信息
     *
     * @param name
     * @return
     */
    Map findByNameKPIClassByZ(@Param("name") String name);

    /**
     * 根据多个大类id获取KPI集合
     *
     * @param ids
     * @return
     */
    List<Map> findByListClassId(@Param("ids") String ids,@Param("domainId")String domainId);

    public List<Map<String, Object>> getCiKpiAll(@Param("kpiName") String kpiName,@Param("domainId")String domainId,@Param("kpiClassId")String kpiClassId);

    /**
     * 查询数量
     *
     * @return
     */
    int getKpi_TypeCount(@Param("search") String search, @Param("isMatch") String isMatch,@Param("domainId") String domainId);

    /**
     * 根据KPiID查询指标某个属性值
     *
     * @param propertyName
     * @param kpiId
     * @return
     */
    Object findByKpiProperty(@Param("propertyName") String propertyName, @Param("kpiId") String kpiId);

    /**
     * 根据CICODE和CI大类ID获取CIID列表
     *
     * @param ciCode
     * @param type
     * @return
     */

    List<String> findCIidList(@Param("ciCode") List<String> ciCode, @Param("type") List<String> type, @Param("valName") String valName, @Param("typeValName") String typeValName);


    /**
              * 根据指标ids获取指标信息
     *
     * @param
     * @param
     * @return
     */
    public List<Map<String, Object>> getKpiInfoByKpiIds(Map<String,Object> itemMap);
    
    public List<Map<String, Object>> getKpiByKpiIds(Map<String,Object> itemMap);
    
    public List<Map<String, Object>> getKpiByKpiNames(Map<String,Object> itemMap);
    
    public List<String> getCiTypeInfoByKpiIds(Map<String,Object> itemMap);
    
    /**
     * 根据指标ids获取指标单位
     *
     * @param
     * @param
     * @return
    */
    public List<Map<String, Object>> getKpiUnitByKpiIds(List<String> list);

    List<String> getCiCode(@Param("list") List<String> getCiCode,@Param("mpCiItem") String mpCiItem,@Param("ciTypeId") String ciTypeId);

    List<Map<String, Object>> getAllKpiClass();

    List<Map<String,Object>> findByListSource(@Param("ids") String ids);

    List<Map<String, Object>> findKpiIdsBySource(@Param("source") String source);

    String findKpiIdBySource(@Param("source") String source);

    List<Map<String, Object>> findCisByClassName(@Param("className") String className);

    List<Map<String, Object>> findCisByType(@Param("type") String type, @Param("typeVal") String typeVal);

    List<Map<String, Object>> findKpiClassByClassId(@Param("name") List<String> name);

    List<Map<String, Object>> findKpiByKpiName(@Param("name") List<String> name);

    String findCisByCiTypeId(@Param("ciTypeId") String ciTypeId);

    String findCisByMap(Map<String, Object> m);
    
    public List<Map<String,Object>> getCiKpiInfosByIds(Map<String,Object> itemMap);
}