
package com.integration.dao;

import com.integration.entity.DataRel;
import com.integration.entity.EmvRequestMessage;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @version 1.0
 * @date 2018-11-28 09:13:28
 */
@Mapper
public interface DataRelDao {

    /**
     * 分页查询
     *
     * @return
     */
    public List<DataRel> getAllPage(Map<String, String> map);

    /**
     * 查询总数
     *
     * @return
     */
    public int getAllCount(Map<String, String> map);

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    public List<DataRel> getAllList(HashMap<Object, Object> params);

    /**
     * 查询单条
     *
     * @return
     */
    public DataRel getInfo(Map<String, String> map);

    /**
     * 修改单条记录
     *
     * @param info
     */
    public int updateInfo(DataRel info);

    /**
     * 新增单条记录
     *
     * @param info
     */
    public int insertInfo(DataRel info);

    /**
     * 删除单条记录
     *
     * @param id
     */
    public int deleteInfo(@Param("id") String id);

    /**
     * 清除全部数据
     *
     * @param relId
     * @return
     */
    public int updateYXBZ(String relId);

    /**
     * 数据导出
     *
     * @param relId
     * @return
     */
    public List<DataRel> exportDataRelToExcel(@Param("relId") String relId,@Param("domainId") String domainId);

    /**
     * 数据导入
     *
     * @param dr
     * @return
     */
    public int importDataRelTobase(DataRel dr);


    /**
     * 根据CIList获取关系集合
     *
     * @return
     */
    public List<DataRel> getDataRelByCiList(@Param("source_ci_id") String source_ci_id, @Param("ciIdList") List<String> ciIdList);
    
    /**
               * 判断CI关系数据是否存在
     *
     * @return
     */
    public List<Map<String,Object>> queryCiDataRelIsExist(@Param("sourceCiCode") String sourceCiCode,@Param("targetCiCode") String targetCiCode,@Param("relId")String relId);
    
    /**
     * 根据CIList获取关系集合
     *
     * @return
     */
    public List<Map<String,Object>> getDataRelByCiIdsList(Map<String,Object> itemMap);

    /**
     * 根据CI编码andtype编码查ciid
     *
     * @param ciBm
     * @param typeBm
     * @return
     */
    public String findCiIdBybmAndType(@Param("ciBm") String ciBm, @Param("typeBm") String typeBm);

    /**
     * 删除重复数据
     *
     * @return
     */
    public int deleteRepeatDateRel();

    /**
     * 获取CI所有上级层级关系
     *
     * @param cId
     * @param sup
     * @return
     */
    public List<DataRel> getSupRelByCI(@Param("cId") String cId, @Param("sup") List sup);

    /**
     * 获取CI所有下级层级关系
     *
     * @param cId
     * @param sub
     * @return
     */
    public List<DataRel> getSubRelByCI(@Param("cId") String cId, @Param("sub") List sub);

    /**
     * 查询源IP和目标IP
     *
     * @return
     */
    public List<Map<String, Object>> findSouTar();

    public List<DataRel> findRelName();

    /**
     * 删除CI数据时，修改关系的有效标志为0
     *
     * @param ciId
     * @return
     */
    public int updateRelByCi(List<String> ciId);

    /**
     * 查询对象关系列表
     *
     * @param emvReturnCIMessageList
     * @return
     */
    List<DataRel> findAllByCiID(@Param("list") List<EmvReturnCIMessage> emvReturnCIMessageList,@Param("cItype") String cItype);

    /**
     * 查询对象关系列表
     *
     * @param emvReturnCIMessageList
     * @return
     */
    List<DataRel> findAllByCiIDFl(@Param("list") List<EmvReturnCIMessage> emvReturnCIMessageList,@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("cIType") String cIType);

    /**
     * 根据多个属性查询列表
     *
     * @param emvRequestMessage
     * @param dataRel
     * @return
     */
    List<DataRel> findRelByAll(@Param("emvRequestMessage") EmvRequestMessage emvRequestMessage, @Param("dataRel") DataRel dataRel);
    
    /**
     * 根据多个ID查询关系表信息
     *
     * @param 
     * @param 
     * @return
     */
    public List<Map<String, Object>> getCiDataRelByIds(@Param("list") List<String> list,@Param("listRel") List<String> listRel);
    
    public List<Map<String, Object>> getCiDataRelBySourceIdAndTargetIdAndRelId(Map<String, Object> itemMap);
    
    public List<Map<String, Object>> getCiDataRelByTargetIdAndSourceIdAndRelId(Map<String, Object> itemMap);

    /**
     * 删除CI数据，将关系真实删除
     * @param ciId
     */
    public int deleteRelByCi(List<String> ciId);
    

    /**
     * 清楚全部数据，真实删除
     * @param relId
     * @return
     */
    int deleteByrelId(String relId);
    
    public int getCiDataRelCountByRuleDown(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByRuleUp(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxAllDown(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxStartDown(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxEndDown(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxAllUp(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxStartUp(Map<String, Object> itemMap);
    
    public int getCiDataRelCountByMinToMaxEndUp(Map<String, Object> itemMap);

    void updateSourceTypeBm(Type type);

    void updateTargetTypeBm(Type type);
}