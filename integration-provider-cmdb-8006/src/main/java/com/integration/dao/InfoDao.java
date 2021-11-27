package com.integration.dao;

import com.integration.entity.EmvRequestMessage;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
* @Package: com.integration.dao
* @ClassName: InfoDao
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 台账信息
*/
@Mapper
@Repository
public interface InfoDao {

	/**
	 * 修改信息
	 *
	 * @param info
	 * @return
	 */
	public int updateInfo(Info info);

	public int deleteInfoByids(List<String> ids);

	/**
	 * 根据大类ID删除信息
	 *
	 * @param tidList
	 * @return
	 */
	public int deleteInfoByTid(List<String> tidList);

	/**
	 * 根据大类ID获取此大类数据ID集合
	 *
	 * @param tid
	 * @return
	 */
	public List<Info> findDataIdByTid(@Param("tid") String tid,@Param("domainId") String domainId);

	/**
	 * 添加字段信息
	 *
	 * @param info
	 * @return
	 */
	public int addTypeInfo(Info info);

	public int addTypeInfos(List<Info> info);

	/**
	 * 查询CI信息列表包括大类编码
	 *
	 * @return
	 */
	public List<LinkedHashMap> findCIInfoListPage(Map map);

	/**
	 * 查询CI信息列表包括大类编码
	 * 维护期设置使用
	 * @return
	 */
	public List<Info> findCIInfoListPageMaintain(@Param("searchName")String searchName,@Param("domainId") String domainId);

	/**
	 * 查询CI信息总数
	 * @return
	 */
	public int findCIInfoListCount(Map map);

	/**
	 * 根据ID查询ci_info
	 *
	 * @param id
	 * @return
	 */
	public Info findInfoById(@Param("id") String id,@Param("domainId") String domainId);

    List<Map> findCiInfoByBM(@Param("ciBm") String ciBm);

	/**
	 * 根据大类ID查询ci信息和某一个属性的值
	 * @param emvRequestMessage
	 * @return
	 */
	List<EmvReturnCIMessage> findIndexByTypeId(EmvRequestMessage emvRequestMessage);

	/**
	 * 根据id获取个数
	 *
	 * @param ciName
	 * @return
	 */
	int findByIdNum(String ciName);

	/**
               * 根据ciBm获取ciIds
     *
     * @param
     * @return
     */
	public List<String> getCiIdsByCiBm(@Param("ciBm") String ciBm);

	/**
	 * 根据大类ID获取此大类数据ID集合
	 *
	 * @param tids
	 * @return
	 */
	public List<Info> findDataIdByTids(@Param("tids") String tids,@Param("domainId") String domainId);

	/**
	 * 根据来源（Source）获取ci集合
	 *
	 * @param source
	 * @return
	 */
	List<Map<String, Object>> findCisBySource(@Param("source") String source);
	
	List<Map<String, Object>> queryCiInfoAll();
	
	Map<String,Object> queryCiInfoByCiId(@Param("ciId")String ciId);

	int updateInfoList(@Param("list") List<Info> infoLists);

	/**
	 * 根据ci bm集合查找列表 ciCode，ciTypeId
	 *
	 * @param dataIds
	 * @return
	 */
	List<Map<String, String>> getCiList(@Param("list") List<String> dataIds,@Param("domainId") String domainId);

    List<String> findItemField(@Param("ciTypeId") String ciTypeId);

	void updateInfoName(@Param("ciTypeId") String ciTypeId,@Param("reField") String reField);

	void updateInfoNames(@Param("ciTypeId") String ciTypeId);
}
