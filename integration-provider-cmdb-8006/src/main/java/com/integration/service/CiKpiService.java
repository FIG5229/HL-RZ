package com.integration.service;

import com.integration.entity.CiKpiInfo;
import com.integration.entity.Kpi_Type;
import com.integration.entity.PageResult;
import com.integration.generator.entity.IomCiKpi;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */

public interface CiKpiService {

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
    public int updateInfo(CiKpiInfo info, String[] ciKpiThres, String[] objectName,
                          HttpServletRequest request);

    /**
     * 新增单条记录
     *
     * @param info
     */
    public int insertInfo(CiKpiInfo info, String[] ciKpiThres, String[] objectName, HttpServletRequest request);

    /**
     * 删除单条记录
     *
     * @param id
     */
    public int deleteInfo(String id);


    /**
     * 获取组合实体
     *
     * @return
     */
    public List<Kpi_Type> getKpi_Type(String search);

    /**
     * 获取组合实体
     *
     * @return
     */
    public List<Kpi_Type> getKpi_Type(String search, String isMatch);

    /**
     * 导入
     * @param list
     * @return
     */
    PageResult importKpiFY(List list);

    /**
     * 根据ci大类id查询kpi
     *
     * @param ciTypeId
     * @return
     */
    public List<CiKpiInfo> selectKpiByCiTypeId(String ciTypeId);

    /**
     * 根据name获取KPIID
     *
     * @param name
     * @return
     */
    CiKpiInfo findByNameKPI(String name);

    /**
     * 根据name获取KPI和大类信息
     *
     * @param name
     * @return
     */
    Map findByNameKPIClass(String name);

    /**
     * 根据name获取KPI和大类信息
     *
     * @param name
     * @return
     */
    Map findByNameKPIClassByZ(String name);

    /**
     * 根据多个大类ID获取KPI集合
     *
     * @param ids
     * @return
     */
    List<Map> findByListClassId(String ids);

    /**
     * 添加
     *
     * @param ciKpiInfo
     * @return
     */
    String saveKpi(CiKpiInfo ciKpiInfo);


    Map<String, Object> saveKpiByPmvSavePerformance(IomCiKpi iomCiKpi);

    List<Map<String, Object>> getCiKpiAll(String kpiName,String kpiClassId);

    /**
     * 查询数量
     *
     * @return
     */
    int getKpi_TypeCount(String search, String isMatch);

    /**
     * 根据KPiID查询指标某个属性值
     *
     * @param propertyName
     * @param kpiId
     * @return
     */
    Object findByKpiProperty(String propertyName, String kpiId);

    /**
     * 根据CICODE和CI大类ID获取CIID列表
     *
     * @param ciCode
     * @param type
     * @return
     */

    List<String> findCIidList(String ciCode, String type,String valName,String typeValName);



	List<Map<String, Object>> getKpiInfoByKpiIds(List<String> list);
	
	List<Map<String, Object>> getKpiUnitByKpiIds(List<String> list);

    List<String> getCiCode(List<String> ciValue);

    /**
     * 获取KPI大类列表
     *
     * @return
     */
    List<Map<String, Object>> selAllKpiClass();

    /**
     * 根据来源获取kpi列表
     *
     * @param ids
     * @return
     */
    List<Map<String, Object>> findByListSource(String ids);

    /**
     * 根据来源查询kpi
     *
     * @param source
     * @return
     */
    List<Map<String, Object>> findKpiIdsBySource(String source);

    /**
     * 根据来源查询kpi
     *
     * @param source
     * @return
     */
    String findKpiIdBySource(String source);

    /**
     * @Method findCisByClassName
     * @Description 根据CI大类名称查询相关的ci信息
     * @Param [className]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/2 15:33
     * @Version 1.0
     * @Exception
     **/
    List<Map<String, Object>> findCisByClassName(String className);

    /**
     * @Method findCisByType
     * @Description
     * @Param [type, typeVal]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/2 16:35
     * @Version 1.0
     * @Exception
     **/
    List<Map<String, Object>> findCisByType(String type, String typeVal);

    /**
     * @Method findKpiClassByClassId
     * @Description 根据大类名称查询KPI大类的信息
     * @Param [id]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/4 14:46
     * @Version 1.0
     * @Exception
     **/
    List<Map<String, Object>> findKpiClassByClassId(List<String> name);

    /**
     * @Method findKpiByKpiName
     * @Description 根据kpi名称查询kpi信息
     * @Param [name]
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/6/4 15:11
     * @Version 1.0
     * @Exception
     **/
    List<Map<String, Object>> findKpiByKpiName(List<String> name);

    /**
     * @Method findCisByCiTypeId
     * @Description 根据ci大类获取ciId集合
     * @Param [ciTypeId]
     * @Return java.lang.String
     * @Author sgh
     * @Date 2020/6/11 9:52
     * @Version 1.0
     * @Exception
     **/
    String findCisByCiTypeId(String ciTypeId);

    /**
     * @Method findCisByMap
     * @Description 根据条件查询ciId（逗号隔开的字符串）
     * @Param [m]
     * @Return java.lang.String
     * @Author sgh
     * @Date 2020/6/12 11:31
     * @Version 1.0
     * @Exception
     **/
    String findCisByMap(Map<String, Object> m);

	String getIomCiKpiTypeByKpiIdAndCiTypeId(String kpiId, String ciTypeId);

	List<CiKpiInfo> selectKpiByCiTypeIdByPaging(String ciTypeId, String pageNum, String pageSize);

	List<Map<String, Object>> getKpiByKpiIds(Map<String,Object> itemMap);

	List<String> getCiTypeInfoByKpiIds(Map<String, Object> itemMap);

	List<CiKpiInfo> selectKpiByCiTypeIdSort(String ciTypeId);

	List<Map<String, Object>> getKpiByKpiNames(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getCiKpiInfosByIds(Map<String,Object> itemMap);

	List<Map<String,Object>> queryPerformanceCurveByCiId(String ciTypeId, String pageNum, String pageSize,String domainId,String kpiName);

	int selectKpiByCiTypeIdByPagingCount(String ciTypeId,String domainId,String kpiName);
	
	public String insertIomCiKpiTypeByKpiIdAndCiTypeId(Map<String,String> map);

	void initializeCiKpiType();

	List<Map<String,Object>> selectKpiByCiTypeIdByPerformanceChart(String ciTypeId,String domainId);

	void initializeCiKpiInfoToRedis();

    void clearCiKpiInfoRedisCache();
}