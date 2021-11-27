package com.integration.dao;

import com.integration.entity.IomCiKpi;
import com.integration.entity.TypeData;
import com.integration.entity.TypeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: TypeItemDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 类定义管理
*/
@Mapper
@Repository
public interface TypeItemDao {

	/**
	 * 根据大类ID获取字段列表
	 *
	 * @param tid
	 * @return
	 */
	public List<TypeItem> findItemByTid(String tid);
	/**
	 * 根据大类ID获取字段列表(驼峰)
	 *
	 * @param tid
	 * @return
	 */
	public List<Map<String,Object>> findItemByTidToHump(String tid);
	
	/**
	 * 根据多个大类ID获取字段列表(驼峰)
	 *
	 * @return
	 */
	public List<Map<String,Object>> findItemByTidsToHump(Map<String,Object> itemMap);

	/**
	 * 获取字段列表
	 * @return
	 */
	public List<TypeItem> findItemList();

	/**
	 * 新增字段
	 *
	 * @param typeItem
	 * @return
	 */
	public int addItem(TypeItem typeItem);

	/**
	 * 获取当前最大的列,用于生成下个映射字段
	 *
	 * @param tid
	 * @return
	 */
	public List<TypeItem> dataNum(String tid);

	/**
	 * 修改字段
	 *
	 * @param typeItem
	 * @return
	 */
	public int updateItem(TypeItem typeItem);

	/**
	 * 删除字段
	 *
	 * @param id
	 * @return
	 */
	public int deleteItem(String id);

	/**
	 * 根据大类ID删除字段
	 *
	 * @param tidList
	 * @return
	 */
	public int deleteItemByTid(List<String> tidList);

	/**
	 * 根据ID获取字段信息
	 *
	 * @param id
	 * @return
	 */
	public TypeItem findTypeItem(String id);

	/**
	 * 根据字段信息查询数据
	 *
	 * @param sql
	 * @return
	 */
	public List<TypeData> findData(@Param("sql") String sql);

	/**
	 * 根据字段信息查询数据
	 *
	 * @param sql
	 * @return
	 */
	public List<Map> findDataMap(@Param("sql") String sql);
    
	/**
	 * 根据字段信息查询数据(驼峰)
	 *
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> findDataToHump(@Param("sql") String sql);
	
	/**
	 * 获取数据总数
	 *
	 * @param tid
	 * @return
	 */
	public int findDataCount(String tid);

	/**
	 * 根据大类ID获取主键映射字段
	 *
	 * @return
	 */
	public String findPK(String tid);


	/**
	 * 判断属性名称是否存在
	 * @param attr_name
	 * @param ci_type_id
	 * @return
	 */
	public int itemNameExists(@Param("attr_name")String attr_name,@Param("ci_type_id")String ci_type_id);

	/**
	 * 根据大类id获取数据字段
	 * @return
	 */
	public String findDataCodebyTid(@Param("tid")String tid);

	/**
     * 根据大类ID删除所有字段
     *
     * @return
     */
	public Integer deleteItemByAllByTid(List<TypeItem> list);

	/**
	 * 根据大类ID，字段ID获取此字段可能的所有值，支持模糊查询
	 * @param typeId
	 * @param itemId
	 * @param value
	 * @return
	 */
	public List<String> findItemVal(@Param("typeId") String typeId,@Param("itemId") String itemId,@Param("value") String value);

	/**
	 * 查询所有KPI数据
	 * @return
	 */
	public List<IomCiKpi> findKpi();

	/**
	 * 根据映射关系查询ci数据
	 * @param list
	 * @return
	 */
	Map<String, Object> getMappingCiData(@Param("list") String[] list);

	/**
	 * 所属机柜为空校验
	 * @return
	 */
	List<Map<String, Object>> jhIsnull(@Param("ciTypeId") String ciTypeId, @Param("sbmc") String sbmc, @Param("checkColumn") String checkColumn) ;

	/**
	 * 所属机柜是否存在校验
	 * @return
	 */
	List<Map<String, Object>> jhIsexis(@Param("ciTypeId") String ciTypeId, @Param("sbbh") String sbbh, @Param("sbmc") String sbmc, @Param("checkColumn") String checkColumn);

	/**
	 * U位数据为空校验
	 * @return
	 */
	List<Map<String, Object>> uDataIsnull(@Param("ciTypeId") String ciTypeId, @Param("sbmc") String sbmc, @Param("checkColumn") String checkColumn);

	/**
	 * U位数据格式校验
	 * @return
	 */
	List<Map<String, Object>> uDatasFormat(@Param("ciTypeId") String ciTypeId, @Param("sbbh") String sbbh, @Param("sbmc") String sbmc, @Param("checkColumn") String checkColumn);

	/**
	 * 查询大类所有的ci属性值
	 * @param ciTypeId
	 * @return
	 */
	List<Map<String, Object>> selectCiAttrs(@Param("ciTypeId") String ciTypeId);

	List<Map<String, Object>> selectJiFangList(@Param("ciTypeId") String ciTypeId,@Param("ciBm") String ciBm);

	/**
	 * 查询大类所有的ci属性值
	 * @param ciTypeId
	 * @return
	 */
	List<Map<String, Object>> selectCiAttrsById(@Param("ciTypeId") String ciTypeId);

	/**
	 * U位数据重复校验
	 * @return
	 */
	List<Map<String, Object>> getRepeatDataId(@Param("ciTypeId") String ciTypeId, @Param("sbbh") String sbbh, @Param("sbmc") String sbmc, @Param("ss") String ss, @Param("checkColumn") String checkColumn);

	/**
	 * 根据大类id查询ciId
	 *
	 * @param ids
	 * @return
	 */
	List<String> findDataIdByTidList(@Param("ids") String ids,@Param("domainId") String domainId);

	/**
	 * 查询ci大类属性
	 *
	 * @param ciClassId
	 * @return
	 */
	List<TypeItem> selItemByTypeId(@Param("ciClassId") String ciClassId);

	/**
	 * 删除字段，真实删除
	 * @param id
	 * @return
	 */
	int deleteItemTrue(String id);

	/**
	 * 根据大类ID删除所有字段
	 * @param list
	 * @return
	 */
	Integer deleteItemByAllByTidTrue(List<TypeItem> list);

	List<String> selItemListByTypeIdAndCiBm(@Param("computerRoomArea") String computerRoomArea,@Param("ciBm") String ciBm);

	List<Map<String, Object>> selectJiFangLists(@Param("ciTypeId") String computerRoomArea,@Param("ciBm") String ciBm,@Param("list") List<String> typeItemId);

	List<TypeItem> selItemByTypeIdAndCiBm(@Param("ciTypeId") String ciTypeId, @Param("ciCode") String ciCode);

	boolean updateItemList(@Param("list") List<TypeItem> typeItemList);

	List<String> findCiTypeListByCiIds(@Param("ciTypeId") String ciTypeId);

	List<Map<String, Object>> selectCiAttrsByIds(@Param("ciTypeId")String ciTypeId, @Param("ciBm") String ciBm, @Param("list") List<String> typeItemId,@Param("ciIds") String ciIds);

	List<Map<String, String>> getMajorList(@Param("domainId") String domainId);

	String findMpCiItemByIdTypeId(@Param("cIType") String cIType,@Param("cIName") String cIName);

	List<String> getCiCodeList(@Param("list") List<String> list);

    List<String> findItemNameByItemId(@Param("list") List<String> asList);

	List<String> findItemValByIdList(List<String> asList);
	/**
	 * @Author: ztl
	 * date: 2021-08-12
	 * @description: 获取所有类定义字段
	 */
	List<Map<String, Object>> findAllItemHump();

	List<Map> findDataMapDeploy(@Param("tid") String tid,@Param("ciPropertyList") List<String> ciPropertyList,@Param("character") String character);
}
