package com.integration.service;

import com.integration.entity.DataIndex;
import com.integration.entity.PageResult;
import com.integration.entity.TypeData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @Package: com.integration.service
* @ClassName: TypeDataService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 类数据管理
*/
public interface TypeDataService {

	/**
	 * 新增数据
	 *
	 * @param typeData
	 * @return
	 */
	public TypeData addData(TypeData typeData, String cjr);

	/**
	 * 删除单条数据
	 *
	 * @param dataId
	 * @return
	 */
	public boolean deleteData(String dataId);

	/**
	 * 根据大类ID删除数据
	 *
	 * @return
	 */
	public boolean deleteDataByTid(String tid);

	/**
	 * 修改数据
	 *
	 * @param typeData
	 * @return
	 */
	public boolean updateData(TypeData typeData) throws Exception;

	/**
	 * 根据ID获取数据
	 *
	 * @param did
	 * @return
	 */
	public TypeData findDataById(String did);

	/**
	 * 导出excel数据
	 *
	 * @param tid
	 */
	public void exportExcelData(HSSFWorkbook workbook, XSSFWorkbook workbook2007, List<String> tid,
			HttpServletResponse response);

	/**
	 * 下载模板
	 *
	 * @param workbook
	 * @param tid
	 * @param response
	 */
	public void downloadTemplet(HSSFWorkbook workbook, XSSFWorkbook workbook2007, List<String> tid,
			HttpServletResponse response);

	/**
	 * excel数据导入
	 *
	 * @return
	 */
	public Map importExcelData(String ci_type_id, List<TypeData> dataList, String cjr);

	/**
	 * 判断添加的主键是否重复
	 *
	 * @param pkName
	 * @param ci_type_id
	 * @return
	 */
	public boolean pkExists(String pkName, String ci_type_id);

	/**
	 * 模糊查询配置信息
	 *
	 * @param map
	 * @return
	 */
	public List<LinkedHashMap> findConfigInfo(Map map);

	/**
	 * CI模糊查询、配置信息展示（驼峰）
	 *
	 * @param map
	 * @return
	 */
	public List<LinkedHashMap> findConfigInfoHump(Map map);

	/**
	 * 模糊查询配置信息
	 *
	 * @param map map
	 * @return 记录总数
	 */
	public int findConfigInfoCount(Map map);

	/**
	 * 根据CIid查询所有关系ci大类
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
	public List<Map> findRelCIDataByCIIdAndCIType(Map map);

	/**
	 * 删除多条数据
	 *
	 * @param dataIds
	 * @return
	 */
	public boolean deleteDataByids(List<String> dataIds);
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
	 * 根据CI和AttrId获取值
	 *
	 * @param ciId
	 * @param attrId
	 * @return
	 */
	String findDataByCiIDAndAttrId(String ciId, String attrId);

	List<Map<String, Object>> findCiTypeIdByCiId(String ciIds,String ciCodes);

	/**
	 * 根据值获取ci和大类信息
	 *
	 * @param maps
	 * @return
	 */
	List<DataIndex> findDataByVal(List<Map> maps);

	/**
	 * 查询ci上下几层的关联ci
	 *
	 * @param ciId    ciId
	 * @param upNum   上N层
	 * @param downNum 下N层
	 * @param relIds  关系ids
	 * @param typeIds 类型ids
	 * @return
	 */
	Map<String, Object> getCiRels(String ciId, Integer upNum, Integer downNum, String relIds, String typeIds,String ciCode);

	/**
	 * 根据大类ID和字段等值条件获取CI信息
	 *
	 * @param sql
	 * @return
	 */
	List<Map> findDataInfo(String sql);

	/**
	 * 获取向上的999层关系
	 *
	 * @param ciId
	 */
	Map<String, Object> getUpCiRels(String ciId,String ciCode);

	/**
	 * 获取向下的999层关系
	 *
	 * @param ciId
	 */
	Map<String, Object> getDownCiRels(String ciId,String ciCode);

	/**
	 * 根据大类id和bm查询唯一数据
	 * @param id
	 * @return
	 */
	int getBMByCiTypeId(String id,String bm);

	Map<String, Object> getRelIdAndCiTypeIdByHierarchy(String ciId, Integer upNum, Integer downNum,String ciCode);

	/**
	 * 查询ciId
	 *
	 * @param cMap
	 * @return
	 */
	List<Map<String,String>> findCiIdByMap(Map<String, Object> cMap);

	Map<String, Object> queryImpactAnalysisByCiId(String ciId,String ciTypeId,Integer upNum,Integer downNum,String ciCode);

	Map<String, Object> queryRootCauseAnalysisByCiId(String ciId, String ciTypeId, Integer upNum, Integer downNum,String ciCode);

	Map<String, Object> queryPathByStartCiIdAndEndCiId(String startCiId, String endCiId, String typeId,String startCiCode,String endCiCode);

	List<Map<String, Object>> findConfigItemByCiCode(String ciName,String domainId);

	Map<String, Object> queryDirectRelationByCiTypeId(String ciTypeId,String ciTypeMc,Integer upNum, Integer downNum);

	List<LinkedHashMap> findConfigInfoNoPageHump(Map map);


	List<String> getAttrValuesList(String ciTypeId, String mpCiItem, String searchCondition, String domainId);

	PageResult findCiByCiCode(Map map);

	List<LinkedHashMap> getCiTypeInfo(Map map);

	Map<String, Object> findCiByCiId(Map map);

	String findDataByCiCodeAndAttrId(String ciCode, String attrId,String ciType);
	
	public List<Map<String,Object>> getWorldMapCiSingleRels(List<String> relIdList,List<String> ciCodeList,String ciTypeName);
	
	public List<Map<String,Object>> getWorldMapCiSingleRelsUp(List<String> relIdList,List<String> ciCodeList,String ciTypeName);

	List<Map<String,Object>> queryImpactAnalysisByCiCodeOptimize(String ciTypeId, String ciCode,List<String> ciTypeIdList,Map<String,Set<String>> ciCodeMap,List<Map<String,Object>> returnRelList,Set<String> historyTypeId,String domainId);
	
	public List<Map<String,Object>> queryRootCauseAnalysisByCiCodeOptimize(String ciTypeId,String ciCode,List<String> ciTypeIdList,Map<String,Set<String>> ciCodeMap,List<Map<String,Object>> returnRelList,Set<String> historyTypeId,String layer,String domainId);

    List<Map> getCommandByCiCode(String ciCode);

    List<Map<String, Object>> findFieldConfCiData(String sourceCiId, String ciTypeId);

	/**
	 * 消费接口平台放到队列的数据，更新ci信息
	 * @param data
	 * @return
	 */
	PageResult updateCI(List<Map<String,Object>> data);
	
	/**
	 * 根据多个ciCode查询imageFullName
	 *
	 * @return
	 */
	public List<Map<String, Object>> getImageFullNameByCiCodes(List<String> ids);

	void exportExcelDataDeploy(HSSFWorkbook workbook, XSSFWorkbook workbook2007, List<String> ciPropertyList, List<String> tid, HttpServletResponse response);
}
