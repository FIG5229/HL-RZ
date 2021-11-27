package com.integration.service;

import com.alibaba.fastjson.JSONObject;
import com.integration.entity.IomCiKpi;
import com.integration.entity.TypeData;
import com.integration.entity.TypeItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
* @Package: com.integration.service
* @ClassName: TypeItemService
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 类定义管理
*/
public interface TypeItemService {
    /**
     * 根据大类ID获取字段列表
     *
     * @param tid
     * @return
     */
    public List<TypeItem> findItemByTid(String tid);

    /**
     * 新增字段
     *
     * @param typeItem
     * @return
     */
    public TypeItem addItem(TypeItem typeItem);

    /**
     * 修改字段
     *
     * @param typeItem
     * @return
     */
    public boolean updateItem(TypeItem typeItem);

    /**
     * 删除字段
     *
     * @param id
     * @return
     */
    public boolean deleteItem(String id);

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
     * @param map
     * @return
     */
    public Map findData(Map<String, String> map);

    /**
     * 根据字段信息查询数据
     *
     * @param map
     * @return
     */
    public Map findDataNoPage(Map<String, String> map);

    Map getCiInfoNoDomain(Map<String, String> map);

    /**
     * 判断属性名称是否存在
     *
     * @param attr_name
     * @param ci_type_id
     * @return
     */
    public boolean itemNameExists(String attr_name, String ci_type_id);

    /**
     * 获取sql
     *
     * @param map
     * @return
     */
    public String getSql(Map<String, String> map);

    /**
     * excel数据导入
     *
     * @param jsonStr
     * @return
     */
    public boolean importExcelItem(String jsonStr, String ci_type_id, HttpServletRequest request);

    /**
     * excel数据导出字段
     */
    public void exportExcelItem(HSSFWorkbook workbook,
                                XSSFWorkbook workbook2007, List<String> tid,
                                HttpServletResponse response);

    /**
     * 根据CI_id集合，获取所有所需CI_信息
     *
     * @param ciList
     * @return
     */
    public List findCiList(List<String> ciList);

    /**
     * 根据TYPE_id 和CI_id 获取所需数据信息
     *
     * @param map
     * @return
     */
    public Map getCiInfo(Map<String, String> map);

    /**
     * 根据标签名获取所有大类字段导入数据库
     *
     * @return
     */
    public void findDfileList(HttpServletRequest request);

    public void findDfileData(String cjr);


    /**
     * 根据大类id获取告警所用CI信息
     *
     * @param map
     * @return
     */
    public List<Map> getAlarmCiInfo(Map<String, String> map);


    /**
     * 根据configid获取告警所用CI信息
     *
     * @param map
     * @return
     */
    public List<Map> getCiInfoByConfigId(Map<String, String> map);

    public String findPK(String tid);

	Integer deleteDataAll(Map<String, String> map);

	/**
	 * 没有分页
	 * @param map
	 * @return
	 */
	String getSqlNoPage(Map<String, String> map);

    /**
     * 根据大类id和Ciid获取数据
     * @param map
     * @return
     */
    TypeData findDataByTid(Map<String,Object> map);

	Integer deleteItemByAllByTid(List<TypeItem> list);

	/**
	 * 根据大类ID，字段ID获取此字段可能的所有值，支持模糊查询
	 * @param typeId
	 * @param itemId
	 * @param value
	 * @return
	 */
	public List<String> findItemVal(String typeId,String itemId,String value);

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
    Map<String, Object> getMappingCiData(String[] list);

    /**
     * 所属机柜为空校验
     * @return
     */
    List<Map<String, Object>> jhIsnull(String ciTypeId, String sbmc, String checkColumn) ;

    /**
     * 所属机柜是否存在校验
     * @return
     */
    List<Map<String, Object>> jhIsexis(String ciTypeId, String sbbh, String sbmc, String checkColumn);

    /**
     * U位数据为空校验
     * @return
     */
    List<Map<String, Object>> uDataIsnull(String ciTypeId, String sbmc, String checkColumn);

    /**
     * U位数据格式校验
     * @return
     */
    List<Map<String, Object>> uDatasFormat(String ciTypeId, String sbbh, String sbmc, String checkColumn);


    /**
     * 查询大类所有的ci属性值
     * @param ciTypeId
     * @return
     */
    List<Map<String, Object>> selectCiAttrs(String ciTypeId);

    /**
     * 查询大类所有的ci属性值
     * @param ciTypeId
     * @return
     */
    List<Map<String, Object>> selectCiAttrsById(String ciTypeId);

    /**
     * U位数据重复校验
     * @return
     */
    List<Map<String, Object>> getRepeatDataId(String ciTypeId, String sbbh, String sbmc, String ss, String checkColumn);

	List<Map<String, Object>> selectJiFangList(String computerRoom, String computerRoomArea,List<String> list);

	List<Map<String, Object>> findComputerRoomInfo(String computerRoom, String computerRoomArea, List<String> listRole,String computerRoomBuilding);

	JSONObject getCiItemByBm(String ciBm);

    /**
     * 根据大类id查询ciId
     *
     * @param ids
     * @return
     */
    List<String> findDataIdByTidList(String ids);

    /**
     * 根据CI_id集合，获取所有所需CI_信息(无数据权限)
     *
     * @param ciList
     * @return
     */
    public List findCiListNoDomain(List<String> ciList);

    /**
     * 查询大类信息
     *
     * @param ciClassId
     * @return
     */
    List<TypeItem> selItemByTypeId(String ciClassId);

    /**
     *根据大类ID获取字段列表及对应的字段数据
     * @param tid 大类ID
     * @param ciId CIID
     * @return
     */
    List<TypeItem> findItemAndDataByTid(String tid, String ciId,String ciCode);

	List<Map<String, Object>> findItemByTidToHump(String tid);

	Map<String, Object> findDataNoPageToHump(Map<String, String> map,List<Map<String,Object>> dataVal);

	String getSqlNoPageToHump(Map<String, String> map);

	List findCiListByDimension(List<String> ciList);

	Map getCiInfoNoDomainByDimension(Map<String, String> map);

    List<Map<String,String>> getSecurityCiInfo(String tid);

    Map<String, String> getSecurityCiInfoByTidAndVague(String tid, String vague);


	List<Map<String, Object>> findItemByTidsToHump(String tid);

    int findItemSortByTid(String ci_type_id);

    boolean updateTypeItem(String typeItemStr);

    String findMpCiItemByIdTypeId(String cIType, String cIName);

    String getCiCodeList(String ciIdList);

    List<String> findItemNameByItemId(List<String> asList);
    /**
     * @Author: ztl
     * date: 2021-08-12
     * @description: 获取所有大类和类定义字段（为接口平台同步数据提供）
     */
    Map<String, Object> getAllCiTypeAndItem();
}
