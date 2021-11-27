package com.integration.service;

import com.github.pagehelper.PageInfo;
import com.integration.entity.EmvRequestMessage;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Info;
import com.integration.generator.entity.IomCiType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 :$Author$
 * @version Revision 创建时间:2018年12月7日 下午3:25:35 id:Id
 */

public interface InfoService {

    /**
     * 查询CI信息列表包括大类编码
     *
     * @return
     */
    public List<LinkedHashMap> findCIInfoList(Map map);

    /**
     * 查询CI信息总数
     *
     * @return
     */
    public int findCIInfoListCount(Map map);

    /**
     * 根据大类ID获取此大类数据ID集合
     *
     * @param tid
     * @return
     */
    public List<Info> findDataIdByTid(String tid);


    /**
     * 根据编码获取id信息
     *
     * @param ciBm
     * @return
     */
    List<Map> findCiInfoByBM(String ciBm);

    /**
     * 维护期设置使用
     *
     * @param
     * @return
     */
    List<Info> findCIInfoListMaintain(String searchName,String domainId);

    /**
     * 根据大类ID查询ci信息和某一个属性的值
     *
     * @param emvRequestMessage
     * @return
     */
    List<EmvReturnCIMessage> findIndexByTypeId(EmvRequestMessage emvRequestMessage);

	/**
	 * 获取可视化建模允许的大类id
	 * @param relId	关系id
	 * @param sourceTypeId	源大类id	（sourceTypeId与targetTypeId有且只能有一个）
	 * @param targetTypeId	目标大类id（sourceTypeId与targetTypeId有且只能有一个）
	 * @param tids	允许的id
	 * @return
	 */
	String[] getCiIdsByCiTypeRel(String relId, String sourceTypeId, String targetTypeId, String[] tids,String relType);

    /**
     * 根据id获取个数
     *
     * @return
     */
    int findByIdNum(String ciName);

	List<String> getCiIdsByCiBm(String ciBm);

	/**
	 * 根据可视化建模查询大类详情
	 * @param relId
	 * @param sourceTypeId
	 * @param targetTypeId
	 * @param tids
	 * @param relType
	 * @return
	 */
	List<IomCiType> selectIomCiType(String relId, String sourceTypeId, String targetTypeId, String[] tids,
			String relType);

	Info findCiInfoById(String id);

	/**
	 * 根据大类ID获取此大类数据ID集合
	 *
	 * @param tids
	 * @return
	 */
	public PageInfo findDataIdByTids(String tids, Integer pageSize, Integer pageNum);
    /**
     * @Method findCisBySource
     * @Description
     * @Param [source] 来源Id
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author sgh
     * @Date 2020/5/25 10:46
     * @Version 1.0
     * @Exception
     **/
    List<Map<String, Object>> findCisBySource(String source);

	void initializeCiInfoToRedis();

	/**
	 * 根据ci bm集合查找列表 ciCode，ciTypeId
	 *
	 * @param dataIds
	 * @return
	 */
	public List<Map<String, String>> getCiList(List<String> dataIds,String domainId);
	/**
	 * @Author: ztl
	 * date: 2021-08-06
	 * @description: 修改台账中CINAME
	 */
	void updateCiInfoName(String ci_type_id);
}
