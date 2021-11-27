package com.integration.service;

import com.integration.entity.Info;
import com.integration.entity.Type;
import com.integration.entity.TypeInfoList;
import com.integration.entity.TypeInfoListHump;

import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service
* @ClassName: TypeService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 大类管理
*/
public interface TypeService {

	/**
	 * 获取所有大类
	 *
	 * @return
	 */
	public List<Type> findTypeList();

	/**
	 * 获取所有大类（驼峰）
	 *
	 * @return
	 */
	List<Map> findTypeListHump();

	/**
	 * 根据查询条件获取大类列表
	 * @param map
	 * @return
	 */
	public List<Type> findTypeListByCond(Map<String, String> map);

	/**
	 * 获取所有大类及大类下的所有CI信息
	 *
	 * @return
	 */
	public List<TypeInfoList> findTypeInfo(String ci_type_id, String search);

	/**
	 * 添加大类
	 *
	 * @return
	 */
	public Type addType(Type type);

	/**
	 * 修改大类
	 *
	 * @return
	 */
	public boolean updateType(Type type);

	/**
	 * 删除大类
	 *
	 * @return
	 */
	public boolean deleteType(String typeId);

	/**
	 * 根据ID获取大类
	 *
	 * @param typeId
	 * @return
	 */
	public Type findTypeById(String typeId);

	/**
	 * 判断大类名字是否存在
	 *
	 * @return
	 */
	public boolean typeNameExists(String ci_type_bm, String ci_type_dir,String domainId);

	/**
	 * 通过名称查询大类
	 * @param name
	 * @return
	 */
	public Type findByMc(String name,String domainId);

	List<Map<String, Object>> findCiTypeIdAndMcByCiId(List<String> list);

	List<Map<String, Object>> findClassAndCiInfoByCiIds(List<String> list);

	Integer findTypeSort(String pId);

	List<Map<String, Object>> findClassInfoAndCiInfoByCiIds(List<String> list);

	List<Map<String, Object>> findTypeByMap(Map<String, Object> map);

	List<Map<String, Object>> findCiInfoByCiIds(Map<String, Object> itemMap);

	List<Map<String, Object>> findClassInfoByCiTypeIds(List<String> ids);

	/**
	 * 获取所有大类搜索用（驼峰 私有）
	 *
	 * 有数据权限限制
	 *
	 * @param type 0:查询，1：添加
	 * @return
	 */
	List<Map> findCiTypeListHumpSearch(int type);

	List<Map<String, Object>> findCiTypeListHumpByCiTypeBm(String ciTypeMc);

	List<Map<String, Object>> findCiTypeListHumpByCiTypeBms(String ciTypeMc);
	/**
	 * 根据ID获取大类信息（无数据域）
	 * @param typeId
	 * @return
	 */
	Type findTypeByIdPublic(String typeId);

	List<Map<String,Object>> findCiTypeListHumpByIds(String tids);

	/**
	 * 获取所有大类及大类下的所有CI信息（驼峰）
	 * @param ciTypeId
	 * @param search
	 * @return
	 */
    List<TypeInfoListHump> findTypeInfoHump(String ciTypeId, String search);

	List<Map<String, Object>> findCiTypeInfoListHumpByIds(String tids);

	List<Map<String,Object>> getCiTypeList();

	List<Map<String, Object>> queryCiInfoByCiTypeName(String ciTypeName,String domainId);

	List<Map<String, Object>> getCiTypeInfoByCiTypeName(String domainId, String ciTypeName);

	List<Map<String, Object>> queryCiInfoByCiTypeNameWorldMap(String ciTypeName);

	List<Map<String, Object>> findCiInfoByBaseMapId(String ciTypeName, String ciCodes);

	List<Info> getCiInfoList(Map<String, Object> map);

	List<String> findCiInfoByCiCodes(Map<String, Object> itemMap);
}
