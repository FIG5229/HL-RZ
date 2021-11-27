package com.integration.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.integration.dao.CiTypeRelDao;
import com.integration.dao.DataRelDao;
import com.integration.dao.TypeDataDao;
import com.integration.entity.DataRel;
import com.integration.entity.EmvReturnCIMessage;
import com.integration.entity.Type;
import com.integration.feign.EmvEvCurrentService;
import com.integration.generator.dao.IomCiDataRelMapper;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.IomCiDataRel;
import com.integration.generator.entity.IomCiDataRelExample;
import com.integration.generator.entity.IomCiDataRelExample.Criteria;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import com.integration.service.DataRelService;
import com.integration.utils.BaseRelUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xxx@163.com">sulq</a>
 * @version 1.0
 * @date 2018-11-28 09:13:28
 */
@Transactional
@Service
public class DataRelServiceImpl implements DataRelService {

    @Resource
    private DataRelDao iDataRelDao;
    @Resource
    private IomCiDataRelMapper iomCiDataRelMapper;

    @Resource
    private EmvEvCurrentService emvEvCurrentService;

    @Resource
    private IomCiTypeMapper iomCiTypeMapper;

    @Resource
    private CiTypeRelDao ciTypeRelDao;
    
    @Autowired
	private TypeDataDao typeDataDao;

    /**
     * 分页查询
     *
     * @param map
     * @return
     */
    @Override
    public List<DataRel> getAllPage(Map<String, String> map) {
        return iDataRelDao.getAllPage(map);
    }

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    @Override
    public int getAllCount(Map<String, String> map) {

        return iDataRelDao.getAllCount(map);
    }

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Override
    public List<DataRel> getAllList(HashMap<Object, Object> params) {

        return iDataRelDao.getAllList(params);
    }

    /**
     * 查询单条
     *
     * @param map
     * @return
     */
    @Override
    public DataRel getInfo(Map<String, String> map) {

        return iDataRelDao.getInfo(map);
    }

    /**
     * 修改单条记录
     *
     * @param info
     */
    @Override
    public int updateInfo(DataRel info) {

        return iDataRelDao.updateInfo(info);
    }

    /**
     * 新增单条记录
     *
     * @param info
     */
    @Override
    public int insertInfo(DataRel info) {

        return iDataRelDao.insertInfo(info);
    }

    /**
     * 删除单条记录
     *
     * @param id
     */
    @Override
    public int deleteInfo(String id) {

        return iDataRelDao.deleteInfo(id);
    }

    /**
     * 清除全部数据
     */
    @Override
    public int updateYXBZ(String relId) {
        // TODO Auto-generated method stub
        return iDataRelDao.updateYXBZ(relId);
    }

    /**
     * 数据导出
     */
    @Override
    public List<DataRel> exportDataRelToExcel(String relId) {
        // TODO Auto-generated method stub
        //获取数据域ID
        String domainId= TokenUtils.getTokenDataDomainId();
        return iDataRelDao.exportDataRelToExcel(relId,domainId);
    }

    /**
     * 数据导入
     */

    @Override
    public int importDataRelTobase(List<DataRel> drList) {
        // TODO Auto-generated method stub
        int a = 0;
        for (DataRel dataRel : drList) {
            a += iDataRelDao.importDataRelTobase(dataRel);
        }

        return a;
    }

    /**
     * 根据CIList获取每个CIID和与它有关系的关系集合
     *
     * @return
     */
    @Override
    public Map<String, List<DataRel>> getDataRelByCiList(List<String> ciIdList) {
        Map<String, List<DataRel>> map = new HashMap<String, List<DataRel>>();
        for (int i = 0; i < ciIdList.size(); i++) {
            List<DataRel> dataRelList = iDataRelDao.getDataRelByCiList(ciIdList.get(i), ciIdList);
            map.put(ciIdList.get(i), dataRelList);
        }
        return map;
    }
    
    /**
     * 根据CIList获取每个CIID和与它有关系的关系集合(8007配置数据)
     *
     * @return
     */
    @Override
    public Map<String, List<Map<String,Object>>> getDataRelByCiIdsList(List<String> ciCodeList) {
        Map<String, List<Map<String,Object>>> map = new HashMap<String, List<Map<String,Object>>>();
        for (int i = 0; i < ciCodeList.size(); i++) {
        	String ciCode=ciCodeList.get(i);
        	Map<String,Object> itemMap=new HashMap<String,Object>();
        	itemMap.put("sourceCiBm", ciCode);
        	String [] ciCodesArray = ciCodeList.toArray(new String[ciCodeList.size()]);
        	itemMap.put("ciCodeList", ciCodesArray);
            List<Map<String,Object>> dataRelList = iDataRelDao.getDataRelByCiIdsList(itemMap);
            map.put(ciCodeList.get(i), dataRelList);
        }
        return map;
    }

    /**
     * 根据ci编码andType编码查询ciid
     *
     * @param ciBm
     * @param typeBm
     * @return
     */
    @Override
    public String findCiIdBybmAndType(String ciBm, String typeBm) {
        // TODO Auto-generated method stub
        return iDataRelDao.findCiIdBybmAndType(ciBm, typeBm);
    }

    /***
     * 根据CIID获取上级或者下级关系
     */
    @Override
    public Map getRelSupAndSubByCI(String cId, String supLayer, String subLayer) {
        // TODO Auto-generated method stub
        Map map = new HashMap();
        List sup = new ArrayList();
        List sub = new ArrayList();
        List<DataRel> supList = new ArrayList<DataRel>();
        List<DataRel> subList = new ArrayList<DataRel>();
        if (StringUtils.isNotEmpty(supLayer) && !"0".equals(supLayer)) {
            for (int i = 0; i < Integer.parseInt(supLayer); i++) {
                sup.add(i + 1);
            }
            supList = iDataRelDao.getSupRelByCI(cId, sup);
        }

        if (StringUtils.isNotEmpty(subLayer) && !"0".equals(subLayer)) {
            for (int i = 0; i < Integer.parseInt(subLayer); i++) {
                sub.add(i + 1);
            }
            subList = iDataRelDao.getSubRelByCI(cId, sub);
        }
        map.put("supList", supList);
        map.put("subList", subList);
        return map;
    }

    /**
     * 查询源IP和目标IP
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findSouTar() {
        return iDataRelDao.findSouTar();
    }

    @Override
    public List<DataRel> findRelName() {
        return iDataRelDao.findRelName();
    }

    public JSONArray selectCountByCiIdList(String[] ciIdList,String[] ciCodeList) {
        List<Map<String, Object>> list = emvEvCurrentService.selectCountByCiIdList(ciIdList,ciCodeList);
        if (list != null && list.size() > 0) {
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(list));
            return array;
        }

        return null;
    }

    /**
     * 获取ci的图标
     *
     * @param iomCiDataRels
     * @return
     */
    private Map<String, String> getCiIcons(List<IomCiDataRel> iomCiDataRels) {
        Map<String, String> resMap = new HashMap<String, String>();
        if (iomCiDataRels != null && iomCiDataRels.size() > 0) {
            List<String> typeIds = new ArrayList<String>();
            for (IomCiDataRel iomCiDataRel : iomCiDataRels) {
                typeIds.add(iomCiDataRel.getSourceTypeId());
                typeIds.add(iomCiDataRel.getTargetTypeId());
            }
            typeIds = typeIds.stream().distinct().collect(Collectors.toList());
            IomCiTypeExample iomCiTypeExample = new IomCiTypeExample();
            com.integration.generator.entity.IomCiTypeExample.Criteria criteria = iomCiTypeExample.createCriteria();
            criteria.andYxbzEqualTo(1);
            criteria.andIdIn(typeIds);
            List<IomCiType> iomCiTypes = iomCiTypeMapper.selectByExample(iomCiTypeExample);
            Map<String, String> typeMaps = new HashMap<String, String>();
            for (String typeId : typeIds) {
                for (IomCiType iomCiType : iomCiTypes) {
                    if (typeId.equals(iomCiType.getId())) {
                        typeMaps.put(typeId, iomCiType.getCiTypeIcon());
                    }
                }
            }

            for (IomCiDataRel iomCiDataRel : iomCiDataRels) {
                resMap.put(iomCiDataRel.getSourceCiId(), typeMaps.get(iomCiDataRel.getSourceTypeId()));
                resMap.put(iomCiDataRel.getTargetCiId(), typeMaps.get(iomCiDataRel.getTargetTypeId()));
            }
        }
        return resMap;
    }

    /**
     * 获取告警数量和封装关联关系
     *
     * @param iomCiDataRels
     * @return
     */
    private Map<String, Object> getEventCounts(List<IomCiDataRel> iomCiDataRels) {
        if (iomCiDataRels != null && iomCiDataRels.size() > 0) {
            List<String> ciIds = new ArrayList<String>();
            List<String> ciCodes = new ArrayList<String>();
            Map<String, String> namsMap = new HashMap<String, String>();
            Map<String, String> typeIds = new HashMap<String, String>();
            for (IomCiDataRel iomCiDataRel : iomCiDataRels) {
                namsMap.put(iomCiDataRel.getSourceCiBm(), iomCiDataRel.getSourceCiBm());
                namsMap.put(iomCiDataRel.getTargetCiBm(), iomCiDataRel.getTargetCiBm());

                typeIds.put(iomCiDataRel.getSourceCiBm(), iomCiDataRel.getSourceTypeId());
                typeIds.put(iomCiDataRel.getTargetCiBm(), iomCiDataRel.getTargetTypeId());
            }
            for (String ciCodeString : namsMap.keySet()) {
                ciCodes.add(ciCodeString);
            }
            if (ciCodes.size() > 0) {
                JSONArray eventCounts = selectCountByCiIdList(ciIds.toArray(new String[ciIds.size()]),ciCodes.toArray(new String[ciCodes.size()]));
                Map<String, Object> resMap = new HashMap<String, Object>();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, String> ciIcons = getCiIcons(iomCiDataRels);
                for (String ci : ciIds) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", ci);
                    map.put("name", namsMap.get(ci));
                    map.put("count", 0);
                    map.put("icon", ciIcons.get(ci));
                    if (eventCounts != null && eventCounts.size() > 0) {
                        for (Object object : eventCounts) {
                            JSONObject obj = (JSONObject) object;
                            if (ci.equals(obj.get("ciId"))) {
                                map.put("count", obj.get("num"));
                                break;
                            }
                        }
                    }
                    list.add(map);
                }
                resMap.put("ciCounts", list);
                resMap.put("ciRels", iomCiDataRels);
                return resMap;
            }
        }
        return null;
    }

    /**
     * 根据对象id获取所有关联的对象及其未关闭告警数量
     *
     * @param ciId
     * @return
     */
    @Override
    public Map<String, Object> selectAllEventCountByCiId(String ciId,String ciCode) {
        if (StringUtils.isNotEmpty(ciCode)) {
            IomCiDataRelExample iomCiDataRelExample = new IomCiDataRelExample();
            Criteria criteria = iomCiDataRelExample.createCriteria();
            criteria.andYxbzEqualTo(1);
            List<IomCiDataRel> iomCiDataRels = iomCiDataRelMapper.selectByExample(iomCiDataRelExample);

            List<IomCiDataRel> resCiDataRels = new ArrayList<IomCiDataRel>();
            List<String> codesTemp = null;
            List<String> ids = new ArrayList<String>();
            List<String> ciCodes = new ArrayList<String>();
            ciCodes.add(ciCode);
            ids.add(ciId);
            IomCiDataRel iomCiDataRel = null;

            //无限遍历树，寻找所有关联到的节点（只找从当前ci出发的）
            while (ciCodes.size() > 0 && iomCiDataRels.size() > 0) {
                codesTemp = new ArrayList<String>();
                for (String code : ciCodes) {
                    for (int i = iomCiDataRels.size() - 1; i >= 0; i--) {
                        iomCiDataRel = iomCiDataRels.get(i);
                        //只找从当前ci出发的关系
                        if (code.equals(iomCiDataRel.getSourceCiBm())) {
                            codesTemp.add(iomCiDataRel.getTargetCiBm());
                            resCiDataRels.add(iomCiDataRel);
                            iomCiDataRels.remove(i);
                        }
                    }
                }
                //去重并且赋值
                ciCodes = codesTemp.stream().distinct().collect(Collectors.toList());
            }

            return getEventCounts(resCiDataRels);
        }
        return null;

    }

    /**
     * 根据对象id获取一级关联的对象及其未关闭告警数量
     *
     * @param ciId
     * @return
     */
    @Override
    public Map<String, Object> selectOneEventCountByCiId(String ciId) {
        if (StringUtils.isNotEmpty(ciId)) {
            IomCiDataRelExample iomCiDataRelExample = new IomCiDataRelExample();
            Criteria criteria = iomCiDataRelExample.createCriteria();
            criteria.andSourceCiIdEqualTo(ciId);
            criteria.andYxbzEqualTo(1);
            List<IomCiDataRel> iomCiDataRels = iomCiDataRelMapper.selectByExample(iomCiDataRelExample);
            return getEventCounts(iomCiDataRels);
        }
        return null;
    }

    /**
     * 根据CIid查询关联对象列表
     *
     * @param emvReturnCIMessageList
     * @return
     */
    @Override
    public List<DataRel> findAllByCiID(List<EmvReturnCIMessage> emvReturnCIMessageList,String cItype) {
        return iDataRelDao.findAllByCiID(emvReturnCIMessageList,cItype);
    }

    /**
     * 根据CIid查询关联对象列表(过滤)
     *
     * @param emvReturnCIMessageList
     * @return
     */
    @Override
    public List<DataRel> findAllByCiIDFl(List<EmvReturnCIMessage> emvReturnCIMessageList, String startDate, String endDate,String cIType) {
        return iDataRelDao.findAllByCiIDFl(emvReturnCIMessageList,startDate,endDate,cIType);
    }

    @Override
    public Map<String, Object> getPath(List<String> startIds, List<String> endIds, List<String> typeIds,List<String> startCiCodes,List<String> endCiCodes) {
        BaseRelUtil relUtil = new BaseRelUtil("sourceCiBm", "targetCiBm") {
            @Override
            public Object getDate(List<String> ids,List<String> ciCodes) {

            	String [] ciCodesArray = ciCodes.toArray(new String[ciCodes.size()]);
				List<String> list=new ArrayList<String>();
				for(int i = 0; i < ciCodesArray.length; i++){
					String ciCodeStr=ciCodesArray[i];
					list=Arrays.asList(ciCodeStr.split(","));
				}
				String [] listArray = list.toArray(new String[list.size()]);
            	Map<String, Object> itemMap=new HashMap<String, Object>();
            	itemMap.put("sourceCiBmList", listArray);
            	itemMap.put("relList", "");
            	itemMap.put("targetTypeIdList", "");
            	List<Map<String,Object>> returnList=typeDataDao.getCiDataRelDownBySourceIdAndTargetId(itemMap);
                return returnList;
            }
        };
        List<Map<String, Object>> list = relUtil.getPath(startIds, endIds,startCiCodes,endCiCodes);
        Map<String, Object> allMap = relUtil.dataFormat(list);
        @SuppressWarnings("unchecked")
        List<String> allIds = (List<String>) allMap.get("ids");
        if (allIds != null && allIds.size() > 0) {
            List<Map<String, Object>> ciList = ciTypeRelDao.selectCiAndType(allIds);
            Map<String, Map<String, Object>> ciMap = new HashMap<String, Map<String, Object>>();
            for (Map<String, Object> map : ciList) {
                ciMap.put(String.valueOf(map.get("id")), map);
            }

            list = checkList(list, typeIds, ciMap);

            Map<String, Object> map = relUtil.dataFormat(list);
            //加入ci大类的类型和数量校验，丰富返回的ci名字和图表之类

            List<String> ids = (List<String>) map.get("ids");
            List<Object> cis = new ArrayList<Object>();
            for (String id : ids) {
                cis.add(ciMap.get(id));
            }
            map.put("cis", cis);
            return map;
        }
        return null;
    }



    private List<Map<String, Object>> checkList(List<Map<String, Object>> list, List<String> typeIds, Map<String, Map<String, Object>> ciMap) {
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();

        //如果大类id集合是null，则视为长度0
        if (typeIds == null) {
            typeIds = new ArrayList<String>();
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            Map<String, Object> one = list.get(i);
            List<String> idsT = (List<String>) one.get("ids");
            //长度是否一致
            if (idsT.size() - 2 == typeIds.size()) {
                boolean flag = true;
                //此时idsT比typeIds大2，如果typeId大于0则需要依次比较，完全一致才算通过，如果typeId等于0则idsT等于2不需要比较
                if (typeIds.size() > 0) {
                    //遍历第二个到倒数第二个
                    for (int j = 1; j < idsT.size() - 1; j++) {
                        Map<String, Object> ci = ciMap.get(idsT.get(j));
                        if (ci == null || !typeIds.get(j - 1).equals(String.valueOf(ci.get("ciTypeId")))) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    res.add(one);
                }
            }

        }

        return res;
    }

    /**
     * 根据多个ID查询关系表信息
     *
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getCiDataRelByIds(List<String> list, List<String> listRel) {
        return iDataRelDao.getCiDataRelByIds(list, listRel);
    }

    /**
     * 清楚全部数据，真实删除
     * @param relId
     * @return
     */
    @Override
    public int deleteByrelId(String relId) {
        return iDataRelDao.deleteByrelId(relId);
    }

    /**
     * 修改源CI类别
     * @param type
     */
    @Override
    public void updateSourceTypeBm(Type type) {
        iDataRelDao.updateSourceTypeBm(type);
    }

    /**
     * 修改目标CI类别
     * @param type
     */
    @Override
    public void updateTargetTypeBm(Type type) {
        iDataRelDao.updateTargetTypeBm(type);
    }
    /**
               * 判断CI关系数据是否存在
     * @param list
     */
    @Override
    public JSONArray queryCiDataRelIsExist(JSONArray jsonArray){
    	if(jsonArray!=null && jsonArray.size()>0) {
    		for(Object arrObject : jsonArray) {
    			JSONObject jsonObject = (JSONObject) arrObject;
    			String sourceCiCode=(String) jsonObject.get("sourceCiCode");
    			String targetCiCode=(String) jsonObject.get("targetCiCode");
    			String relId=(String) jsonObject.get("relId");
    			List<Map<String,Object>> listData=iDataRelDao.queryCiDataRelIsExist(sourceCiCode, targetCiCode, relId);
    			if(listData==null || listData.size()==0) {
    				List<Map<String,Object>> dataList=new ArrayList<>();
    				Map<String,Object> mapData=new HashMap<>();
    				mapData.put("sourceCiCode", sourceCiCode);
    				mapData.put("targetCiCode", targetCiCode);
    				mapData.put("relId", relId);
    				mapData.put("yxbz", 0);
    				mapData.put("isDelete", 1);
    				dataList.add(mapData);
    				jsonObject.put("datas", dataList);
    			}else {
    				if(listData.size()==1) {
    					Map<String,Object> mapData=listData.get(0);
    					int yxbz=(int) mapData.get("yxbz");
    					if(0==yxbz) {
    						String sourceCiCodeStr=(String) mapData.get("sourceCiCode");
    						String targetCiCodeStr=(String) mapData.get("targetCiCode");
    						if(sourceCiCodeStr==null || "".equals(sourceCiCodeStr)) {
    							mapData.put("sourceCiCode", sourceCiCode);
    						}
    						if(targetCiCodeStr==null || "".equals(targetCiCodeStr)) {
    							mapData.put("targetCiCode", targetCiCode);
    						}
    						jsonObject.put("datas", listData);
    					}else {
    						jsonObject.put("datas", new ArrayList<Map<String,Object>>());
    					}
    				}else {
    					Map<String,Object> map1=listData.get(0);
    					Map<String,Object> map2=listData.get(1);
    					Integer yxbz1=(Integer) map1.get("yxbz");
    					Integer yxbz2=(Integer) map2.get("yxbz");
    					if(yxbz1==0 && yxbz2==0) {
    						List<Map<String,Object>> listDatas=new ArrayList<>();
    						listDatas.add(map1);
    						jsonObject.put("datas", listDatas);
    					}
    					if((yxbz1==0 && yxbz2==1) || (yxbz1==1 && yxbz2==0)) {
    						jsonObject.put("datas", listData);
    					}
    				}
    			}
    		}
    		return jsonArray;
    	}
    	return null;
    }
}