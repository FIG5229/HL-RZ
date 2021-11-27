package com.integration.dao;

import com.integration.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: TypeDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 大类管理
*/
@Mapper
@Repository
public interface TypeDao {

	/**
	 * 获取所有大类
	 *
	 * @return
	 */
	public List<Type> findTypeList(@Param("domainId") String domainId);

	/**
	 * 获取所有大类（驼峰）
	 *
	 * @return
	 */
	public List<Map> findTypeListHump(@Param("domainId") String domainId);
	
	/**
	 * 获取所有大类（驼峰）
	 *
	 * @return
	 */
	public List<Map<String,Object>> findCiTypeListHumpByIds(Map<String,Object> itemMap);
	
	/**
	 * 获取所有大类（驼峰）并可以根据ciTypeBm模糊查询
	 *
	 * @return
	 */
	public List<Map<String, Object>> findCiTypeListHumpByCiTypeBm(@Param("domainId") String domainId,@Param("ciTypeMc")String ciTypeMc);

	/**
	 * 根据查询条件获取大类列表
	 * @param map
	 * @return
	 */
	public List<Type> findTypeListByCond(Map<String, String> map);

	public List<Map<String, Object>> findClassInfoByCiTypeIds(List<String> list);

	public List<TypeInfoList> findTypeAllList(@Param("search") String search,@Param("domainId") String domainId);



	/**
	 * 获取所有大类及大类下的所有CI信息
	 *
	 * @return
	 */
	public List<TypeInfoList> findTypeInfo(
			@Param("ci_type_id") String ci_type_id,
			@Param("search") String search);

	/**
	 * 添加大类
	 *
	 * @return
	 */
	public int addType(Type type);

	/**
	 * 修改大类
	 *
	 * @return
	 */
	public int updateType(Type type);

	/**
	 * 删除大类
	 *
	 * @return
	 */
	public int deleteType(String typeId);

	/**
	 * 根据目录ID删除大类
	 *
	 * @param dirId
	 * @return
	 */
	public int deleteTypeByDirId(String dirId);

	/**
	 * 根据ID获取大类
	 *
	 * @param typeId
	 * @return
	 */
	public Type findTypeById(@Param("typeId") String typeId,@Param("domainId") String domainId);

	/**
	 * 根据目录ID获取大类ID集合
	 *
	 * @param dirId
	 * @return
	 */
	public List<String> findTypeIdByDid(String dirId);

	/**
	 * 判断大类名字是否存在
	 *
	 * @return
	 */
	public int typeNameExists(@Param("ci_type_bm") String ci_type_bm,@Param("domainId") String domainId);

	/**
	 * 根据大类ID获取大类下CI数量
	 *
	 * @param tid
	 * @return
	 */
	public int ciCountByTid(@Param("tid") String tid,@Param("domainId") String domainId);

	/**
	 * 根据大类ID获取此大类数据ID集合
	 *
	 * @return
	 */
	public List<Info> findDataIdByTid(@Param("ci_type_id") String ci_type_id,
			@Param("search") String search);

	/**
	 * 通过名称查询大类
	 * @param name
	 * @return
	 */
	Type findByMc(@Param("name") String name,@Param("domainId") String domainId);

	public List<Map<String,Object>> findCiTypeIdAndMcByCiId(List<String> list);

	/**
     * 根据多个ciIds查询对应大类信息和ci信息
     * @param
     * @return
     */
	public List<Map<String, Object>> findClassAndCiInfoByCiIds(List<String> list);

	/**
     * 根据多个ciIds查询对应大类信息和ci信息(dmv)
     * @param
     * @return
     */
	public List<Map<String, Object>> findClassInfoAndCiInfoByCiIds(List<String> list);
	/**
     * 根据多个ciIds查询ci信息(dmv)
     * @param
     * @return
     */
	public List<Map<String, Object>> findCiInfoByCiIds(Map<String, Object> itemMap);
	
	/**
     * 根据多个ciCodes查询ci是否存在
     * @param
     * @return
     */
	public List<String> findCiInfoByCiCodes(Map<String, Object> itemMap);

	public List<Map<String, Object>> findCiInfoNumByGroupId(@Param("search") String search,@Param("domainId") String domainId);

	public List<TypeInfoList> getCiTypeAllListByCiTypeId(Map<String, Object> itemMap);

	public List<Info> getCiInfoByLikeCiBmAndCiTypeId(@Param("search") String search,@Param("ciTypeId") String ciTypeId,@Param("domainId")String domainId);

	Integer findTypeSort(@Param("pId") String pId);

	/**
	 * @Method findTypeByMap
	 * @Description 根据大类属性的参数查询
	 * @Param [map]
	 * @Return java.util.Map<java.lang.String,java.lang.Object>
	 * @Author sgh
	 * @Date 2020/6/12 9:45
	 * @Version 1.0
	 * @Exception
	 **/
	List<Map<String, Object>> findTypeByMap(Map<String, Object> map);
	
	List<Map<String, Object>> queryCiInfoByCiTypeName(@Param("ciTypeName")String ciTypeName,@Param("domainId")String domainId);
	
	List<Map<String, Object>> queryCiInfoByCiTypeNameWorldMap(@Param("ciTypeName")String ciTypeName);

	/**
	 *获取所有大类搜索用
	 * @param domainId 数据域权限
	 * @param typeIdList 无权限CI大类ID列表
	 * @return
	 */
	List<Map> findCiTypeListHumpSearch(@Param("domainId") String domainId,@Param("typeIdList") List<String> typeIdList);
	
	public List<Map<String, Object>> getCiInfoNumByCiTypeId(Map<String, Object> itemMap);
	
	public List<Map<String, Object>> findCiInfoByBaseMapId(Map<String, Object> itemMap);

    List<TypeInfoListHump> findTypeAllListHump(@Param("search") String search,@Param("domainId") String domainId);

	List<InfoHump> findDataIdByTidHump(@Param("ciTypeId") String ciTypeId, @Param("search") String search);

	List<TypeInfoListHump> getCiTypeAllListByCiTypeIdHump(Map<String, Object> itemMap);

	List<InfoHump> getCiInfoByLikeCiBmAndCiTypeIdHump(@Param("search") String search,@Param("ciTypeId") String ciTypeId,@Param("domainId") String domainId);

	List<Map<String, Object>> getCiTypeList(@Param("domainId") String domainId);
	
	List<Map<String,Object>> getCiTypeInfoByCiTypeName(@Param("domainId") String domainId,@Param("ciTypeName") String ciTypeName);

	List<Type> getAllCiTypeList(@Param("domainId") String domainId);

	List<Info> getCiInfoList(Map<String, Object> map);
	/**
	 * @Author: ztl
	 * date: 2021-09-03
	 * @description: 根据模糊查询条件模糊匹配CI大类ID
	 */
    List<String> getTypeIdListCondition(@Param("ciPropertyList") List<String> ciPropertyList,@Param("domainId") String domainId);
}
