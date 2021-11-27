package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.*;
import com.integration.generator.dao.IomCiKpiTypeMapper;
import com.integration.generator.dao.IomCiTypeMapper;
import com.integration.generator.entity.IomCiKpiType;
import com.integration.generator.entity.IomCiKpiTypeExample;
import com.integration.generator.entity.IomCiType;
import com.integration.generator.entity.IomCiTypeExample;
import com.integration.service.*;
import com.integration.utils.DataUtils;
import com.integration.utils.token.TokenUtils;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.integration.controller.CiKpiController.bean2map;

/**
* @Package: com.integration.controller
* @ClassName: TypeController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 大类管理
*/
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private TypeItemService typeItemService;
    @Resource
    private IomCiKpiTypeMapper iomCiKpiTypeMapper;
    @Resource
    private IomCiTypeMapper iomCiTypeMapper;
    @Autowired
    private DataRelService dataRelService;
    @Autowired
    private CiInfoToInterfaceService ciInfoToInterfaceService;
    @Autowired
    private CiKpiService ciKpiService;

    /**
     * 获取所有大类
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeList", method = RequestMethod.GET)
    public List<Type> findTypeList() {
        return typeService.findTypeList();
    }
    /**
     * 获取所有大类（驼峰）
     *
     * 接口改造：支持根据CI大类模糊查询、支持根据kpiId进行查询
     *
     * @param ciTypeName
     * @param kpiId
     * @return
     * @date 2020-07-15
     * @author ztl
     */
    @RequestMapping(value = "/findCiTypeListHump", method = RequestMethod.GET)
    public List findTypeListHump(String ciTypeName,String kpiId,String kpiName) {
        if (kpiName != null && !"".equals(kpiName)){
            //获取数据域ID
            String domainId = TokenUtils.getTokenOrgDomainId();
    		List<String> kpiNameList=Arrays.asList(kpiName.split(","));
    		List<String> kpiIdValues=new ArrayList<>();
    			List<Map<String, Object>> listData=ciKpiService.getKpiInfoByKpiIds(kpiNameList);
    			if(listData!=null && listData.size()>0) {
//    				Map<String, Object> map=listData.get(0);
//    				String kpiIdStr=(String) map.get("id");
//    				kpiId=kpiIdStr;
    				for(Map<String, Object> map:listData) {
    					String kpiIdStr=(String) map.get("id");
    					kpiIdValues.add(kpiIdStr);
    				}
    			}	
            
            List<Map<String, Object>> returnList=new ArrayList<>();
            IomCiKpiTypeExample example=new IomCiKpiTypeExample();
            example.createCriteria().andYxbzEqualTo(1).andKpiIdIn(kpiIdValues);
            List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
            if (list == null || list.size() == 0) {
                return null;
            }
            List<String> ciTypeIdsList=new ArrayList<>();
            if(list!=null && list.size()>0) {
                for(IomCiKpiType iomCiKpiType :list) {
                    String ciTypeId=iomCiKpiType.getObjId();
                    ciTypeIdsList.add(ciTypeId);
                }
            }

            IomCiTypeExample exampleCiType=new IomCiTypeExample();
            if (domainId!=null){
                exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList).andDomainId(domainId);
            }else{
                exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList);
            }
            List<IomCiType> list2=iomCiTypeMapper.selectByExample(exampleCiType);
            if(list2!=null && list2.size()>0) {
                for(IomCiType iomCiType:list2){
                    try {
                        Map<String, Object> map=bean2map(iomCiType);
                        returnList.add(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return returnList;
        }
        return typeService.findCiTypeListHumpByCiTypeBm(ciTypeName);
    }


    /**
     * 获取所有大类（驼峰）
     *
     * 接口改造：支持根据CI大类模糊查询、支持根据kpiId进行查询
     *
     * @param ciTypeName
     * @param kpiId
     * @return
     * @date 2020-07-15
     * @author ztl
     */
    @RequestMapping(value = "/findCiTypeListHumps", method = RequestMethod.GET)
    public List findTypeListHumps(String ciTypeName,String kpiId,String kpiName) {
        if (kpiName != null && !"".equals(kpiName)){
            //获取数据域ID
            String domainId = null;
            List<String> kpiNameList=Arrays.asList(kpiName.split(","));
            List<Map<String, Object>> listData=ciKpiService.getKpiInfoByKpiIds(kpiNameList);
            if(listData!=null && listData.size()>0) {
                Map<String, Object> map=listData.get(0);
                String kpiIdStr=(String) map.get("id");
                kpiId=kpiIdStr;
            }

            List<Map<String, Object>> returnList=new ArrayList<>();
            IomCiKpiTypeExample example=new IomCiKpiTypeExample();
            example.createCriteria().andYxbzEqualTo(1).andKpiIdEqualTo(kpiId);
            List<IomCiKpiType> list=iomCiKpiTypeMapper.selectByExample(example);
            if (list == null || list.size() == 0) {
                return null;
            }
            List<String> ciTypeIdsList=new ArrayList<>();
            if(list!=null && list.size()>0) {
                for(IomCiKpiType iomCiKpiType :list) {
                    String ciTypeId=iomCiKpiType.getObjId();
                    ciTypeIdsList.add(ciTypeId);
                }
            }

            IomCiTypeExample exampleCiType=new IomCiTypeExample();
            if (domainId!=null){
                exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList).andDomainId(domainId);
            }else{
                exampleCiType.createCriteria().andYxbzEqualTo(1).andIdIn(ciTypeIdsList);
            }
            List<IomCiType> list2=iomCiTypeMapper.selectByExample(exampleCiType);
            if(list2!=null && list2.size()>0) {
                for(IomCiType iomCiType:list2){
                    try {
                        Map<String, Object> map=bean2map(iomCiType);
                        returnList.add(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return returnList;
        }
        return typeService.findCiTypeListHumpByCiTypeBms(ciTypeName);
    }
    
    /**
     * 根据多个大类ID获取大类信息（驼峰）
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeListHumpByIds", method = RequestMethod.POST)
    public List<Map<String,Object>> findCiTypeListHumpByIds(String tids) {
        return typeService.findCiTypeListHumpByIds(tids);
    }
    
    /**
     * 根据多个大类ID获取大类信息（驼峰,无数据域）
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeInfoListHumpByIds", method = RequestMethod.POST)
    public List<Map<String,Object>> findCiTypeInfoListHumpByIds(String tids) {
        return typeService.findCiTypeInfoListHumpByIds(tids);
    }
    
    /**
               * 获取所有大类（驼峰）并可以根据ciTypeMc模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeListHumpByCiTypeBm", method = RequestMethod.GET)
    public List<Map<String, Object>> findCiTypeListHumpByCiTypeBm(String ciTypeMc) {
        return typeService.findCiTypeListHumpByCiTypeBm(ciTypeMc);
    }
    
    /**
               * 世界地图查询第一层CI信息
     *
     * @return
     */
    @RequestMapping(value = "/findCiInfoByBaseMapId", method = RequestMethod.POST)
    public List<Map<String, Object>> findCiInfoByBaseMapId(String ciTypeName,String ciCodes) {
    	
        return typeService.findCiInfoByBaseMapId(ciTypeName,ciCodes);
    }

    /**
     * 获取所有大类搜索用（驼峰 私有）
     *
     * 有数据权限限制
     * @param type 0:查询，1：添加
     * @return
     */
    @RequestMapping(value = "/findCiTypeListHumpSearch", method = RequestMethod.GET)
    public List<Map> findCiTypeListHumpSearch(int type) {
        return typeService.findCiTypeListHumpSearch(type);
    }


    /**
     * 新增大类
     *
     * @param type
     * @param request
     * @return
     */
    @OptionLog(desc = "新增大类", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public Object addType(Type type, HttpServletRequest request) {
        type.setCjr_id(TokenUtils.getTokenUserId());
        Type result = typeService.addType(type);
        if (result != null) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.setMessage(true, "添加成功!");
        } else {
            PageResult.setMessage(false, "添加失败!");
        }
        return result;
    }

    /**
     * 删除大类
     *
     * @param typeId
     * @param request
     * @return
     */
    @OptionLog(desc = "删除大类", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    public boolean deleteType(String typeId, HttpServletRequest request) {
        int typeItemCount = typeItemService.findItemByTid(typeId).size();
        if ( typeItemCount> 0 ) {
            PageResult.fail("大类下存在类定义或类数据，无法删除!");
            return false;
        }
        boolean result = typeService.deleteType(typeId);
        if (result) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.success("删除成功!");
        } else {
            PageResult.fail("删除失败!");
        }
        return result;
    }

    /**
     * 修改大类
     *
     * @param type
     * @param request
     * @return
     */
    @OptionLog(desc = "修改大类", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    public boolean updateType(Type type, HttpServletRequest request) {
        String domainId = TokenUtils.getTokenOrgDomainId();
        type.setXgr_id(TokenUtils.getTokenUserId());
        type.setCi_type_bm(type.getCi_type_mc());
        Type t = typeService.findTypeById(type.getId());
        if (!t.getCi_type_mc().equals(type.getCi_type_mc())) {
            boolean sf = typeService.typeNameExists(type.getCi_type_mc(), "0",domainId);
            if (sf) {
                PageResult.fail("大类名称已存在!");
                return false;
            }
        }
        boolean flag = typeService.updateType(type);
        if (flag) {
            //修改大类名称以后，关系管理中的大类名称也要进行修改
            //修改源大类编码
            dataRelService.updateSourceTypeBm(type);
            //修改目标大类编码
            dataRelService.updateTargetTypeBm(type);
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.success("修改成功!");
        } else {
            PageResult.fail("修改失败!");
        }
        return flag;
    }

    /**
     * 根据ID获取大类信息
     *
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/findTypeById", method = RequestMethod.POST)
    public Type findTypeById(String typeId) {
        return typeService.findTypeById(typeId);
    }

    /**
     * 根据ID获取大类信息（无数据域）
     *
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/findTypeByIdPublic", method = RequestMethod.POST)
    public Type findTypeByIdPublic(String typeId) {
        return typeService.findTypeByIdPublic(typeId);
    }

    /**
     * 判断大类名称是否存在
     *
     * @param ci_type_bm
     * @param ci_type_dir
     * @return
     */
    @OptionLog(desc = "判断大类名称是否存在", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/typeNameExists", method = RequestMethod.GET)
    public boolean typeNameExists(String ci_type_bm, String ci_type_dir) {

        boolean result = typeService.typeNameExists(ci_type_bm, ci_type_dir,null);
        if(result){
            PageResult.success();
        }else{
            PageResult.fail();
        }

        return result;
    }

    /**
     * 获取所有大类及大类下的所有CI信息
     *
     * @return
     */
    @RequestMapping(value = "/typeInfo", method = RequestMethod.POST)
    public List<TypeInfoList> typeInfo(String map) throws JsonMappingException, JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        // datTableRow是一个数据行对象，从datTableRow获取JSON格式字符串
        Map<String, Object> tmpMap = mapper.readValue(map, Map.class);

        List<TypeInfoList> list = typeService.findTypeInfo(
                tmpMap.get("ciTypeId").toString(), tmpMap.get("ser")
                        .toString());
        return list;

    }

    /**
     *获取所有大类及大类下的所有CI信息（驼峰）
     * @param ciTypeId
     * @return
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws IOException
     */
    @RequestMapping(value = "/typeInfoHump", method = RequestMethod.POST)
    public List<TypeInfoListHump> typeInfoHump(String ciTypeId, String ser) {
        List<TypeInfoListHump> list = typeService.findTypeInfoHump(ciTypeId,ser);
        return list;

    }

    /**
     * 根据多个ciIds获取对应大类Id和名称
     *
     * @return
     */
    @RequestMapping(value = "/findCiTypeIdAndMcByCiId", method = RequestMethod.POST)
    public List<Map<String, Object>> findCiTypeIdAndMcByCiId(String ciCodes) {
        if (ciCodes != null && !"".equals(ciCodes)) {
            List<String> list = Arrays.asList(ciCodes.split(","));
            List<Map<String, Object>> listMap = typeService.findCiTypeIdAndMcByCiId(list);
            return listMap;
        }
        return null;
    }

    /**
     * 根据多个ciIds查询对应大类信息和ci信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findClassAndCiInfoByCiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> findClassAndCiInfoByCiIds(String ciIds,String ciCodes) {
        if (ciCodes != null && !"".equals(ciCodes)) {
            List<String> list = Arrays.asList(ciCodes.split(","));
            List<Map<String, Object>> listMap = typeService.findClassAndCiInfoByCiIds(list);
            return listMap;
        }
        return null;
    }
    
    /**
     * 根据多个ciIds查询对应大类信息和ci信息(dmv)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findClassInfoAndCiInfoByCiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> findClassInfoAndCiInfoByCiIds(String ciIds,String ciCodes) {
        if (ciCodes != null && !"".equals(ciCodes)) {
            List<String> list = Arrays.asList(ciCodes.split(","));
            List<Map<String, Object>> listMap = typeService.findClassInfoAndCiInfoByCiIds(list);
            return listMap;
        }
        return null;
    }
    
    /**
     * 根据多个ciIds查询ci信息(dmv)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByCiIds", method = RequestMethod.POST)
    public List<Map<String, Object>> findCiInfoByCiIds(String ciCodes) {
    	//获取域ID
    	String domainId = TokenUtils.getTokenDataDomainId();
        if (ciCodes != null && !"".equals(ciCodes)) {
            List<String> list = Arrays.asList(ciCodes.split(","));
            Map<String, Object> itemMap = new HashMap<String, Object>();
            if(ciCodes!=null && !"".equals(ciCodes)) {
    			itemMap.put("ciCodeList", ciCodes.split(","));
    		}else {
    			itemMap.put("ciCodeList", "");
    		}
            
            if(domainId!=null && !"".equals(domainId)) {
    			itemMap.put("domainId", domainId);
    		}else {
    			itemMap.put("domainId", "");
    		}
            List<Map<String, Object>> listMap = typeService.findCiInfoByCiIds(itemMap);
            return listMap;
        }
        return null;
    }
    
    /**
     * 根据多个ciCodes查询Ci是否存在
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByCiCodes", method = RequestMethod.POST)
    public List<String> findCiInfoByCiCodes(String ciCodes) {
    	//获取域ID
    	String domainId = TokenUtils.getTokenDataDomainId();
        if (ciCodes != null && !"".equals(ciCodes)) {
            List<String> list = Arrays.asList(ciCodes.split(","));
            Map<String, Object> itemMap = new HashMap<String, Object>();
            if(ciCodes!=null && !"".equals(ciCodes)) {
    			itemMap.put("ciCodeList", ciCodes.split(","));
    		}else {
    			itemMap.put("ciCodeList", "");
    		}
            
            if(domainId!=null && !"".equals(domainId)) {
    			itemMap.put("domainId", domainId);
    		}else {
    			itemMap.put("domainId", "");
    		}
            List<String> listData = typeService.findCiInfoByCiCodes(itemMap);
            List<String> returnList=new ArrayList<>();
            if(listData==null || listData.size()==0) {
            	return list;
            }else {
            	for(String ciCode:list) {
                	boolean flag=listData.contains(ciCode);
                	if(!flag) {
                		returnList.add(ciCode);
                	}
                }
            }
            
            return returnList;
        }
        return null;
    }

    /**
     * @Method findTreeSort
     * @Description
     * @Param []
     * @Return int
     * @Author tzq
     * @Date 2020/4/13 13:54
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/findTypeSort",method = RequestMethod.GET)
    public int findTypeSort(String pId) {
        Integer sort = typeService.findTypeSort(pId);
        if (sort == null) {
            return 1;
        }
        return sort+1;
    }


    /**
     * @Method findTypeByMap
     * @Description 根据大类属性的参数查询
     * @Param [map]
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author sgh
     * @Date 2020/6/12 9:45
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping("findTypeByMap")
    public List<Map<String, Object>> findTypeByMap(@RequestBody Map<String, Object> map) {
        return typeService.findTypeByMap(map);
    }
    
    /**
     * @Method 
     * @Description 根据大类名称查询该大类下CI信息
     * @Param 
     * @Return 
     * @Author tzq
     * @Date 2021/04/16 9:45
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/queryCiInfoByCiTypeName",method = RequestMethod.GET)
    public Map<String, Object> queryCiInfoByCiTypeName(String ciTypeName) {
    	//获取域ID
    	String domainId = TokenUtils.getTokenOrgDomainId();
    	List<Map<String, Object>> ciInfos=typeService.queryCiInfoByCiTypeName(ciTypeName,domainId);  	
    	List<Map<String, Object>> ciTypeInfoList=typeService.getCiTypeInfoByCiTypeName(domainId,ciTypeName);
    	Map<String,Object> returnMap=new HashMap<String,Object>();
    	returnMap.put("ciData", ciInfos);
    	returnMap.put("ciTypeData", ciTypeInfoList);
    	return returnMap;
    }
    
    /**
     * @Method 
     * @Description 根据多个大类名称查询该大类下CI信息
     * @Param 
     * @Return 
     * @Author tzq
     * @Date 2021/05/13
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/queryCiInfoByCiTypeNames",method = RequestMethod.POST)
    public List<Map<String, Object>> queryCiInfoByCiTypeNames(String ciTypeNames) {   	
    	//获取域ID
    	String domainId = TokenUtils.getTokenDataDomainId();
    	List<String> domainIdList=null;
    	if(domainId!=null && !"".equals(domainId)) {
    		domainIdList=Arrays.asList(domainId.split(","));
    	}
    	List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
    	if(ciTypeNames!=null && !"".equals(ciTypeNames)) {
    		List<String> ciTypeNameList=Arrays.asList(ciTypeNames.split(","));
    		for(String ciTypeName:ciTypeNameList) {
    			if(domainIdList!=null && domainIdList.size()>0) {
    				for(String str:domainIdList) {
        				List<Map<String, Object>> ciInfos=typeService.queryCiInfoByCiTypeName(ciTypeName,str);
        				returnList.addAll(ciInfos);
        			}
    			}else {
    				List<Map<String, Object>> ciInfos=typeService.queryCiInfoByCiTypeName(ciTypeName,"");
    				returnList.addAll(ciInfos);
    			}
    			
    		}
    	}
    	return returnList;
    }
    
    /**
     * @Method 
     * @Description 根据大类名称查询该大类下CI信息
     * @Param 
     * @Return 
     * @Author tzq
     * @Date 2021/04/16 9:45
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/queryCiInfoByCiTypeNameWorldMap",method = RequestMethod.GET)
    public Map<String, Object> queryCiInfoByCiTypeNameWorldMap(String ciTypeName) {
    	//获取域ID
    	String domainId = TokenUtils.getTokenDataDomainId();
    	List<Map<String, Object>> ciInfos=typeService.queryCiInfoByCiTypeNameWorldMap(ciTypeName);  	
    	List<Map<String, Object>> ciTypeInfoList=typeService.getCiTypeInfoByCiTypeName(domainId,ciTypeName);
    	Map<String,Object> returnMap=new HashMap<String,Object>();
    	returnMap.put("ciData", ciInfos);
    	returnMap.put("ciTypeData", ciTypeInfoList);
    	return returnMap;
    }


    /**
     * @Method
     * @Description 获取路由列表
     * @Param
     * @Return
     * @Author zf
     * @Date 2021年6月9日
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/queryROUTERList",method = RequestMethod.GET)
    public PageResult queryROUTERList(String ciTypeName) {
        Map<String, Object> map = new HashMap<>();
        map.put("ciTypeName", ciTypeName);
        List<Info> ciInfos=typeService.getCiInfoList(map);
        if(ciInfos == null || ciInfos.size() == 0){
            return DataUtils.returnPr(Collections.EMPTY_LIST);
        }

        return DataUtils.returnPr(ciInfos);
    }

    /**
     * @Method
     * @Description 获取交换机列表
     * @Param
     * @Return
     * @Author zf
     * @Date 2021年6月9日
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/querySWITCHList",method = RequestMethod.GET)
    public PageResult querySWITCHList(String domainId, String ciTypeName) {

        Map<String, Object> map = new HashMap<>();
        map.put("ciTypeName", ciTypeName);
        map.put("domainId", domainId);
        List<Info> ciInfos=typeService.getCiInfoList(map);
        if(ciInfos == null || ciInfos.size() == 0){
            return DataUtils.returnPr(Collections.EMPTY_LIST);
        }

        return DataUtils.returnPr(ciInfos);
    }

    /**
     * @Method
     * @Description 获取交换机列表
     * @Param
     * @Return
     * @Author zf
     * @Date 2021年6月9日
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/querySWITCHListPage",method = RequestMethod.GET)
    public PageResult querySWITCHListPage(String domainId, String attrsStr,String ciTypeName, Integer pageSize, Integer pageNum) {

        if(pageSize == null || pageSize < 1 || pageSize > 100){
            pageSize = 10;
        }
        if(pageNum == null || pageNum < 1 ){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("ciTypeName", ciTypeName);
        map.put("attrsStr", attrsStr);
        map.put("domainId", domainId);
        List<Info> ciInfos=typeService.getCiInfoList(map);
        if(ciInfos == null || ciInfos.size() == 0){
            return DataUtils.returnPr(Collections.EMPTY_LIST);
        }
        PageInfo pageInfo = new PageInfo(ciInfos);
        PageResult pg = DataUtils.returnPr(ciInfos);
        pg.setTotalResult(Long.valueOf(pageInfo.getTotal()).intValue());
        pg.setTotalPage(pageInfo.getPages());
        pg.setReturnObject(ciInfos);

        return pg;
    }


    /**
     * @Method
     * @Description 获取防火墙列表
     * @Param
     * @Return
     * @Author zf
     * @Date 2021年6月9日
     * @Version 1.0
     * @Exception
     **/
    @RequestMapping(value = "/queryFIREWALLList",method = RequestMethod.GET)
    public PageResult queryFIREWALLList(String domainId, String ciTypeName) {
        Map<String, Object> map = new HashMap<>();
        map.put("ciTypeName", ciTypeName);
        map.put("domainId", domainId);
        List<Info> ciInfos=typeService.getCiInfoList(map);
        if(ciInfos == null || ciInfos.size() == 0){
            return DataUtils.returnPr(Collections.EMPTY_LIST);
        }

        return DataUtils.returnPr(ciInfos);
    }


    /**
     * 模糊搜索组装ci信息查询串
     * @param search
     * @return
     */
    public List<String> rebuildSearch(String ciTypeName, String search){
        if(StringUtils.isEmpty(ciTypeName) || StringUtils.isEmpty(search)){
            return null;
        }

        Type type = typeService.findByMc(ciTypeName,null);
        if(type == null){
            return null;
        }

        List<TypeItem> itemList = typeItemService.findItemByTid(type.getId());
        if(itemList == null || itemList.size() == 0){
            return null;
        }
        List<String> result = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        itemList.forEach(item->{
            sb.append("'%\"");
            sb.append(item.getAttr_name());
            sb.append("\":\"%");
            sb.append(search);
            sb.append("%\"%'");

            result.add(sb.toString());
            sb.delete(0, sb.length());
        });

        return result;
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        sb.append("222");
        System.out.println(sb.toString());
        sb.delete(0,sb.length());
        System.out.println(sb.toString());
    }
}
