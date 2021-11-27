package com.integration.service;

import com.alibaba.fastjson.JSONArray;
import com.integration.entity.DataRel;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @date 2018-11-28 09:13:28
 * @version 1.0
 */

 public interface DataRelService {

 	/**
	 * 分页查询
	 * @return
	 */
	public List<DataRel> getAllPage(Map<String,String> map);

	/**
	 * 查询总数
	 * @return
	 */
	public int getAllCount(Map<String,String> map);

	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<DataRel> getAllList(HashMap<Object,Object> params);

	/**
	 * 查询单条
	 * @return
	 */
	public DataRel getInfo(Map<String,String> map);

		/**
	 * 修改单条记录
	 * @param info
	 */
	public int  updateInfo(DataRel info);

		/**
	 * 新增单条记录
	 * @param info
	 */
	public int  insertInfo(DataRel info);

	/**
	 * 删除单条记录
	 * @param id
	 */
	public int deleteInfo(String id);

	/**
	 * 清除全部数据
	 * @param relId
	 * @return
	 */
	public int updateYXBZ(String relId);

	/**
	 * 数据导出
	 * @param relId
	 * @return
	 */
	public List<DataRel> exportDataRelToExcel(String relId);

	/**
	 * 数据导入
	 */
	public int importDataRelTobase(List<DataRel> drList);


	/**
	 * 根据CIList获取关系集合
	 * @return
	 */
	public Map<String,List<DataRel>> getDataRelByCiList(List<String> ciIdList);


	/**
	 * 根据ci编码andType编码查询ciid
	 * @return
	 */
	public String findCiIdBybmAndType(String ciBm, String typeBm);

	/**
	 * 根据CIID获取上级或者下级关系
	 * @param cId
	 * @param supLayer
	 * @param subLayer
	 * @return
	 */
	public Map getRelSupAndSubByCI(String cId, String supLayer, String subLayer);

	/**
	 * 查询源IP和目标IP
	 * @return
	 */
	public List<Map<String,Object>> findSouTar();

	public List<DataRel> findRelName();

	/**
	 * 根据对象id获取所有关联的对象及其未关闭告警数量
	 * @param ciId
	 * @return
	 */
	Map<String, Object> selectAllEventCountByCiId(String ciId,String ciCode);

	/**
	 * 根据对象id获取一级关联的对象及其未关闭告警数量
	 *
	 * @param ciId
	 * @return
	 */
	Map<String, Object> selectOneEventCountByCiId(String ciId);

	/**
	 * 根据CIid查询列表
	 * @param emvReturnCIMessageList
	 * @return
	 */
	List<DataRel> findAllByCiID(List<EmvReturnCIMessage> emvReturnCIMessageList,String cItype);

	/**
	 * 根据CIid查询列表(过滤)
	 * @param emvReturnCIMessageList
	 * @return
	 */
	List<DataRel> findAllByCiIDFl(List<EmvReturnCIMessage> emvReturnCIMessageList,String startDate,String endDate,String cIType);

	/**
	 * 查询路径
	 * @param startIds
	 * @param endIds
	 * @param typeIds
	 * @return
	 */
	Map<String, Object> getPath(List<String> startIds, List<String> endIds, List<String> typeIds,List<String> startCiCodes,List<String> endCiCodes);

	List<Map<String, Object>> getCiDataRelByIds(List<String> list,List<String> listRel);

	/**
	 * 清除全部数据，真实删除
	 * @param relId
	 * @return
	 */
	int deleteByrelId(String relId);

	/**
	 * 修改源CI类别
	 * @param type
	 */
	void updateSourceTypeBm(Type type);

	/**
	 * 修改目标CI类别
	 * @param type
	 */
	void updateTargetTypeBm(Type type);

	Map<String, List<Map<String,Object>>> getDataRelByCiIdsList(List<String> ciIdList);
	
	public JSONArray queryCiDataRelIsExist(JSONArray jsonArray);
}