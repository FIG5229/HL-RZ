package com.integration.dao;

import com.integration.entity.DataIndex;
import com.integration.entity.Info;
import com.integration.entity.TypeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: TypeDataDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类数据管理
*/
@Mapper
@Repository
public interface TypeDataDao {

    /**
     * 新增数据
     *
     * @param typeData
     * @return
     */
    public int addData(TypeData typeData);


    /**
     * 批量查询主键的值
     *
     * @param ids
     * @param mp
     * @return
     */
    public List<String> findPkValue(@Param("ids") List<String> ids, @Param("mp") String mp);


    /**
     * 删除单条数据
     *
     * @param dataId
     * @return
     */
    public int deleteData(String dataId);

    /**
     * 删除多条数据
     *
     * @param dataIds
     * @return
     */
    public int deleteDataByids(List<String> dataIds);

    /**
     * 根据大类ID删除数据
     *
     * @param tidList
     * @return
     */
    public int deleteDataByTid(List tidList);

    /**
     * 修改数据
     *
     * @param typeData
     * @return
     */
    public int updateData(TypeData typeData);

    public int updateDatas(@Param("list") List<TypeData> dataList);

    /**
     * 根据ID获取数据
     *
     * @param did
     * @return
     */
    public TypeData findDataById(@Param("did") String did,@Param("domainId") String domainId);

    /**
     * 根据映射字段获取映射数据
     */
    public String findDataByMp(@Param("sql") String sql);

    /**
     * 判断添加的主键是否重复
     *
     * @param sql
     * @return
     */
    public int pkExists(@Param("sql") String sql);

    /**
     * excel数据导入
     *
     * @return
     */
    public int importExcelData(@Param("dataList") List<TypeData> dataList);

    /**
     * 模糊查询配置信息
     *
     * @param map
     */
    public List<LinkedHashMap> findConfigInfoPage(Map map);

    public List<Map<String, Object>> findConfigItemByCiCode(@Param("ciName")String ciName,@Param("domainId")String domainId);

    /**
     * CI模糊查询、配置信息展示（驼峰）
     *
     * @param map
     * @return
     */
    public List<LinkedHashMap> findHumpConfigInfoPage(Map map);

    /**
     * 模糊查询配置信息
     *
     * @param map map
     * @return 记录总数
     */
    public int findConfigInfoCount(Map map);

    /**
     * 根据ciid查询关系id的大类
     *
     * @param id
     * @return
     */
    public List<Map> findRelCITypeByCIId(String id);

    /**
     * 根据CIid,关系CI大类查询关系数据
     *
     * @return
     */
    public List<Map> findRelCIDataByCIIdAndCIType(@Param("sql") String sql);

    /**
     * 根据大类ID获取数据集合
     *
     * @param tid
     * @return
     */
    public List<TypeData> finDataByTypeId(String tid);

    public List<Map> findEmv();

    public List<Map> findPmv();

    /**
     * 根据Ci和AttrId获取值
     *
     * @param ciId
     * @param attrId
     * @return
     */
    String findDataByCiIDAndAttrId(@Param("ciId") String ciId, @Param("attrId") String attrId);

    public List<Map<String, Object>> findCiTypeIdByCiId(List<String> list);

    /**
     * 根据值获取ci和大类信息
     * @param maps
     * @return
     */
    List<DataIndex> findDataByVal(@Param("list") List<Map> maps);

    /**
     * 根据大类Id和ciId获取数据
     * @param map
     * @return
     */
    TypeData findDataByTid(Map map);

    /**
     * 根据大类ID和字段等值条件获取CI信息
     * @param sql
     * @return
     */
    List<String> findDataInfo(@Param("sql") String sql);

    /**
     * 根据CIID集合获取CIINFO表信息
     * @param ciids
     * @return
     */
    List<Info> findCiInfoById(List<String> ciids);

    /**
     * 根据CIID获取ci和大类的信息汇总
     * @param ciids
     * @return
     */
    List<Map> findCiTypeInfo(List<String> ciids);

    /**
     * 根据CIID获取CI信息
     * @param id
     * @return
     */
    LinkedHashMap findCiDataInfo(String id);


    int getBMByCiTypeId( @Param("bm") String bm,@Param("id") String id);

    /**
     * 根据typeid，ciId删除data表data?数据
     * @param ci_id
     * @param typeId
     * @param dataNo
     * @return
     */
	public int deleteDataByTypeAndCI(@Param("ci_id")String ci_id, @Param("typeId")String typeId, @Param("dataNo")String dataNo);

	public List<Map<String, Object>> getCiDataRelDownBySourceIdAndTargetId(Map<String, Object> itemMap);

	public List<Map<String,Object>> getCiDataRelByImpactAnalysisAndRootCauseAnalysisSource(Map<String, Object> itemMap);
	
	public List<Map<String,Object>> getCiDataRelByImpactAnalysisAndRootCauseAnalysisTarget(Map<String, Object> itemMap);

	public List<Map<String, Object>> getCiDataRelUpBySourceIdAndTargetId(Map<String, Object> itemMap);

	public List<Map<String, Object>> queryCiTypeRelBySourceIdAndTargetId(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> queryCiTypeRelBySourceIdAndTargetIdUp(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> queryCiTypeRelBySourceIdAndTargetIdDown(Map<String, Object> itemMap);

	/**
     * 查询CIid分组
     *
     * @param cMap
     * @return
     */
    List<Map<String,String>> findCiIdByMap(Map<String, Object> cMap);

    /**
     *
     * @param ciId
     * @param mp_ci_item
     * @return
     */
    String finDataByidAndItem(@Param("ciId") String ciId,@Param("mpCiItem") String mp_ci_item);

    /**
     * 真实删除CI数据
     * @param dataIds
     * @return
     */
    int deleteDataByidTrue(List<String> dataIds);

    int deleteDataByidstrue(List<String> dataIds);

    List<LinkedHashMap> findConfigInfoNoPageHump(Map map);

    List<Map<String,String>> getSecurityCiInfo(@Param("tid") String tid,@Param("sql") String sql);

    Map<String, String> getSecurityCiInfoByTidAndVague(@Param("tid") String tid,@Param("vague") String vague,@Param("sql") String sql);

    int findItemSortByTid(String ci_type_id);

    List<String> getAttrValuesList(@Param("ciTypeId") String ciTypeId,@Param("mpCiItem") String mpCiItem,@Param("searchCondition") String searchCondition,@Param("domainId") String domainId);

    List<Map<String, Object>> findCiByCiCode(Map map);

    List<LinkedHashMap> getCiTypeInfo(Map map);

    List<Map<String, Object>> getLabelCiInfo(@Param("sqlStr") String sqlStr,@Param("domainId") String domainId);

    Map<String, Object> findCiByCiId(Map map);
    /**
     * @Author: ztl
     * date: 2021-08-06
     * @description: 根据ciCode获取CIID
     */
    String findCiIdByCiCode(@Param("tid") String tid,@Param("ciCode") String ciCode);

    String findDataByCiCodeAndAttrId(@Param("ciCode") String ciCode,@Param("attrId") String attrId,@Param("ciType") String ciType);

    List<Map> getCommandClassList();

    List<Map> getClassList(String ci_type_id);

    List<Map> getCommandCiList(@Param("map") Map map,@Param("list") List<Map> classList,@Param("ciCode")String ciCode);

    List<Map<String, Object>> getCiDataList(@Param("ciTypeId") String ciTypeId,@Param("itemList") List<String> itemList);

    TypeData findDataByIds(@Param("did") String did,@Param("domainId") String domainId);
}
