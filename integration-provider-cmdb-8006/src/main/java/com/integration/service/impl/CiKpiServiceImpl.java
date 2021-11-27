package com.integration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.integration.dao.*;
import com.integration.entity.*;
import com.integration.feign.DictService;
import com.integration.feign.DomainService;
import com.integration.generator.dao.IomCiKpiMapper;
import com.integration.generator.entity.IomCiKpi;
import com.integration.service.CiKpiService;
import com.integration.service.IomCiKpiClassService;
import com.integration.utils.DateUtils;
import com.integration.utils.RedisUtils;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-11 05:46:51
 */
@Transactional
@Service
public class CiKpiServiceImpl implements CiKpiService {

    @Autowired
    private CiKpiDao iCiKpiDao;

    @Autowired
    private CiKpiTypeDao ciKpiTypeDao;

    @Autowired
    private TypeDao typeDao;

    @Resource
    private CiKpiThresDao ciKpiThresDao;

    @Autowired
    private IomCiKpiClassService iomCiKpiClasService;

    @Resource
    private IomCiKpiMapper iomCiKpiMapper;

    @Resource
    private DictService dictService;

    @Resource
    private DomainService domainService;

    @Resource
    private IomCiKpiClassDao iomCiKpiClassDao;

    @Resource
    private TypeItemDao typeItemDao;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public List<CiKpiInfo> getAllPage(HashMap<String, String> params) {

        return iCiKpiDao.getAllPage(params);
    }

    /**
     * 查询总数
     *
     * @param params
     * @return
     */
    @Override
    public int getAllCount(HashMap<Object, Object> params) {

        return iCiKpiDao.getAllCount(params);
    }

    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Override
    public List<CiKpiInfo> getAllList(HashMap<Object, Object> params) {

        return iCiKpiDao.getAllList(params);
    }

    /**
     * 查询单条
     *
     * @param kpiId
     * @return
     */
    @Override
    public CiKpiInfo getInfo(String kpiId) {

        return iCiKpiDao.getInfo(kpiId);
    }

    /**
     * 修改单条记录
     *
     * @param info
     */
    @Override
    public int updateInfo(CiKpiInfo info, String[] ciKpiThres, String[] objectName,
                          HttpServletRequest request) {
       //更新数据的同时也更新redis---start
       List<String> ciTypeIdList=iCiKpiDao.getObjIdByKpiId(info.getId());
       List<String> addCiTypeIds=new ArrayList<String>();
       if(objectName.length<ciTypeIdList.size()) {
    	   if(objectName.length==0) {
    		  for(String ciTypeId:ciTypeIdList) {
    			  String key="pmvCiKpiType_"+info.getId()+"_"+ciTypeId;
    			  if (RedisUtils.exists(key)) {
                      RedisUtils.remove(key);
    			  }
    		  } 
    	   }else {
    		  List<String> list=Arrays.asList(objectName);
    		  for(String ciTypeId:ciTypeIdList) {
    			  boolean flag=list.contains(ciTypeId);
    			  if(!flag) {
    				  String key="pmvCiKpiType_"+info.getId()+"_"+ciTypeId;
    				  if (RedisUtils.exists(key)) {
                          RedisUtils.remove(key);
        			  }
    			  }
    		  } 
    	   }
       }else if(objectName.length>ciTypeIdList.size()) {
    	   List<String> list=Arrays.asList(objectName);
    	   for(String ciTypeId:list) {
    		   boolean flag=ciTypeIdList.contains(ciTypeId);
    		   if(!flag) {
    			   addCiTypeIds.add(ciTypeId); 
    		   }
    	   }
       }
       //更新数据的同时也更新redis---end
       
        //获取数据域ID
       String domainId = TokenUtils.getTokenOrgDomainId();
        //修改模型
        info.setKpi_class_id(info.getKpi_class_id()==null?"0": "".equals(info.getKpi_class_id())?"0":info.getKpi_class_id());
        info.setDomain_id(domainId);
        //修改模型更新redis备份
        int i = iCiKpiDao.updateInfo(info);
        CiKpiInfo redisInfo = iCiKpiDao.getInfo(info.getId());
        String redis_key = "kpi_"+redisInfo.getKpi_name();
        try {
            Map<String, Object> map = bean2map(redisInfo);
            RedisUtils.set(redis_key,map,20*60*1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除对象和指标值
        ciKpiTypeDao.deleteByKpiId(info.getId());
        ciKpiThresDao.deleteByKpiId(info.getId());
        //对象
        if(objectName != null && objectName.length > 0 ){
            for (String id : objectName) {
                //创建对象
                CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
                //id
                typeInfo.setId(SeqUtil.nextId() + "");
                //模型id
                typeInfo.setKpi_id(info.getId());
                //对象id
                typeInfo.setObj_id(id);
                //对象类型
                typeInfo.setObj_type(2);
                //创建时间
                String time1 = DateUtils.getDate();
                typeInfo.setCjsj(time1);
                //有限标志
                typeInfo.setYxbz(1);
                //新增对象
                i = ciKpiTypeDao.insertInfo(typeInfo);
                //新增redis
                boolean flag=addCiTypeIds.contains(id);
                if(flag) {
                  String key="pmvCiKpiType_"+info.getId()+"_"+id;
   				  if (!RedisUtils.exists(key)) {
   					try {
						Map<String, Object> map = bean2map(typeInfo);
                        RedisUtils.set(key,map,20*60*1000L);
					} catch (Exception e) {
						e.printStackTrace();
					}
       			  }
                }
            }
        }


        //指标值
        //校验ciKpiThres是否有值
        if (ciKpiThres != null && ciKpiThres.length > 0) {
            for (String name : ciKpiThres) {
                CiKpiThres ciKpiThre = new CiKpiThres();
                ciKpiThre.setId(SeqUtil.nextId() + "");
                ciKpiThre.setKpi_id(info.getId());
                ciKpiThre.setKpi_thres(name);
                ciKpiThre.setCjsj(DateUtils.getDate());
                ciKpiThre.setYxbz("1");
                i = ciKpiThresDao.insertInfo(ciKpiThre);
            }
        }


        if (i > 0) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * 新增单条记录
     *
     * @param info
     */
    @Override
    public int insertInfo(CiKpiInfo info, String[] ciKpiThres, String[] objectName, HttpServletRequest request) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        //增加模型

        //模型id
        info.setId(SeqUtil.nextId() + "");
        //有效标志
        info.setYxbz(1);
        //创建人
        String userId = TokenUtils.getTokenUserId();
        //登录人id
        info.setCjr_id(userId);
        //创建时间
        String time = DateUtils.getDate();
        info.setCjsj(time);
        //有效标志
        info.setYxbz(1);
        //数据域ID
        info.setDomain_id(domainId==null?"-1":domainId);
        //kpi_class_id
        info.setKpi_class_id(info.getKpi_class_id()==null?"0": "".equals(info.getKpi_class_id())?"0":info.getKpi_class_id());
        //新增模型
        int i = iCiKpiDao.insertInfo(info);
        
        //新增模型后再redis备份
        String key = "kpi_"+info.getKpi_name();
    	if (!RedisUtils.exists(key)) {
			try {
				Map<String, Object> map = bean2map(info);
                RedisUtils.set(key,map,20*60*1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}  		
		}
        
        //增加对象组
        if(objectName != null && objectName.length > 0 ){
            if(i > 0){
                List<CiKpiTypeInfo> list = new ArrayList<CiKpiTypeInfo>();
                for (String id : objectName) {
                    //创建对象
                    CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
                    //id
                    typeInfo.setId(SeqUtil.nextId() + "");
                    //模型id
                    typeInfo.setKpi_id(info.getId());
                    //对象id
                    typeInfo.setObj_id(id);
                    //对象类型
                    typeInfo.setObj_type(2);
                    //创建时间
                    String time1 = DateUtils.getDate();
                    typeInfo.setCjsj(time1);
                    //有限标志
                    typeInfo.setYxbz(1);
                    //新增对象
                    list.add(typeInfo);
                    //redis备份
                    String keyObj="pmvCiKpiType_"+typeInfo.getKpi_id()+"_"+typeInfo.getObj_id();
                    if (!RedisUtils.exists(keyObj)) {
						try {
							Map<String, Object> map1 = bean2map(typeInfo);
                            RedisUtils.set(keyObj,map1,20*60*1000L);
						} catch (Exception e) {
							e.printStackTrace();
						}   					
                    }
                }
                i = ciKpiTypeDao.insertInfoList(list);
            }
        }

        //增加指标值
        if (ciKpiThres != null && ciKpiThres.length > 0) {
            List<CiKpiThres> list2 = new ArrayList<CiKpiThres>();
            for (String name : ciKpiThres) {
                CiKpiThres ciKpiThre = new CiKpiThres();
                ciKpiThre.setId(SeqUtil.nextId() + "");
                ciKpiThre.setKpi_id(info.getId());
                ciKpiThre.setKpi_thres(name);
                ciKpiThre.setCjsj(DateUtils.getDate());
                ciKpiThre.setYxbz("1");
                list2.add(ciKpiThre);
            }
            i = ciKpiThresDao.insertInfoList(list2);
        }

        return i;
    }

    /**
     * 获取模型和对象的组合实体
     *
     * @return
     */
    @Override
    public List<Kpi_Type> getKpi_Type(String search) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        ArrayList<Kpi_Type> kpi_types = new ArrayList<>();
        //获取所有模型
        List<CiKpiInfo> list = iCiKpiDao.getAllInfo(search,domainId);
        for (CiKpiInfo ciKpiInfo : list) {
            Kpi_Type kpi_type = new Kpi_Type();
            kpi_type.setId(ciKpiInfo.getId());
            kpi_type.setKpi_desc(ciKpiInfo.getKpi_desc());
            kpi_type.setVal_unit(ciKpiInfo.getVal_unit());
            kpi_type.setKpi_name(ciKpiInfo.getKpi_name());
            kpi_type.setKpi_alias(ciKpiInfo.getKpi_alias());
            kpi_type.setIs_match(ciKpiInfo.getIs_match());
            kpi_type.setKpi_class_id(ciKpiInfo.getKpi_class_id());
            kpi_type.setKpi_class_name(ciKpiInfo.getKpi_class_name());
            //获取模型下的所有对象
            List<CiKpiTypeInfo> list1 = ciKpiTypeDao.getObjByKpiId(ciKpiInfo.getId());
            //通过对象id获取对象名称
            List<Type> types = new ArrayList<>();
            for (CiKpiTypeInfo ciKpiTypeInfo : list1) {
                Type type = typeDao.findTypeById(ciKpiTypeInfo.getObj_id(),domainId);
                types.add(type);
            }
            kpi_type.setObj_name(types);
            //指标值
            //根据kpiid获取
            List<CiKpiThres> thresList  = ciKpiThresDao.findByKpiId(ciKpiInfo.getId());
            kpi_type.setThres(thresList);
            kpi_types.add(kpi_type);
        }
        return kpi_types;
    }

    /**
     * 获取模型和对象的组合实体
     *
     * @return
     */
    @Override
    public List<Kpi_Type> getKpi_Type(String search, String isMatch) {

        ArrayList<Kpi_Type> kpi_types = new ArrayList<>();
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        //获取所有模型
        List<CiKpiInfo> list = iCiKpiDao.getAllInfo(search, isMatch,domainId);
        List<Type> allType = typeDao.getAllCiTypeList(domainId);
        List<CiKpiTypeInfo> ciKpiTypeInfoList = ciKpiTypeDao.getAllCikpiTypeInfo();
        List<CiKpiThres> ciKpiThresList = ciKpiThresDao.getAllCiKpiThres();
        for (CiKpiInfo ciKpiInfo : list) {
            Kpi_Type kpi_type = new Kpi_Type();
            kpi_type.setId(ciKpiInfo.getId());
            kpi_type.setKpi_desc(ciKpiInfo.getKpi_desc());
            kpi_type.setVal_unit(ciKpiInfo.getVal_unit());
            kpi_type.setKpi_name(ciKpiInfo.getKpi_name());
            kpi_type.setKpi_alias(ciKpiInfo.getKpi_alias());
            kpi_type.setIs_match(ciKpiInfo.getIs_match());
            kpi_type.setKpi_class_id(ciKpiInfo.getKpi_class_id());
            kpi_type.setKpi_class_name(ciKpiInfo.getKpi_class_name());
            kpi_type.setMaximum(ciKpiInfo.getMaximum());
            kpi_type.setMinimum(ciKpiInfo.getMinimum());
            kpi_type.setMaximums(ciKpiInfo.getMaximum()==null?null:ciKpiInfo.getMaximum().toString());
            kpi_type.setMinimums(ciKpiInfo.getMinimum()==null?null:ciKpiInfo.getMinimum().toString());
            //获取模型下的所有对象
            List<CiKpiTypeInfo> list1 =  ciKpiTypeInfoList.stream().filter(e->e.getKpi_id().equals(ciKpiInfo.getId())).collect(Collectors.toList());
            //通过对象id获取对象名称
            List<Type> types = new ArrayList<>();
            for (CiKpiTypeInfo ciKpiTypeInfo : list1) {
                Type type = null;
                if (allType!=null && allType.size()>0){
                    type = allType.stream().filter(o->o.getId().equals(ciKpiTypeInfo.getObj_id())).findAny().orElse(null);
                }
                if(type!=null){
                    types.add(type);
                }

            }
            kpi_type.setObj_name(types);
            //指标值
            //根据kpiid获取
            List<CiKpiThres> thresList = new ArrayList<>();
            if (ciKpiThresList!=null && ciKpiThresList.size()>0){
                thresList = ciKpiThresList.stream().filter(e->e.getKpi_id().equals(ciKpiInfo.getId())).collect(Collectors.toList());
            }
            kpi_type.setThres(thresList);
            kpi_types.add(kpi_type);
        }
        return kpi_types;
    }


    @Override
    public PageResult importKpiFY(List list){
        PageResult pageResult = new PageResult();
        String result = "";
        try{

            //获取数据域ID
            String domainId = TokenUtils.getTokenOrgDomainId();
            //检查导入的数据是否有重复
            HashSet<String> set = new HashSet<String>();
            for (int i = 0 ; i < list.size() ; i++) {
                List ls = (List)list.get(i);
                String kpiName = ls.get(0).toString();
                boolean bool = set.add(kpiName);
                if(!bool){
                    pageResult.setReturnBoolean(false);
                    pageResult.setReturnMessage("["+ kpiName + "]重复，不可导入");
                    return pageResult;
                }
            }

            //在数据获取导入的数据的其他字段
            List kpiNames = new ArrayList();
            list.forEach(ls -> kpiNames.add(((List)ls).get(0)));
            List<Kpi_Type> listKpiType = new ArrayList<>();
            List<Kpi_Type> newLists = new ArrayList<>();
            newLists.addAll(kpiNames);
            //限制条数,【修改这里】
            int pointsDataLimit = 50;
            Integer size = list.size();
            //判断是否有必要分批
            if (pointsDataLimit < size) {
                //分批数
                int part = size / pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    listKpiType.addAll(iCiKpiDao.getKpiTypes(newLists.subList(0, pointsDataLimit))) ;
                    newLists.subList(0, pointsDataLimit).clear();
                }
                if (!newLists.isEmpty()) {
                    listKpiType.addAll(iCiKpiDao.getKpiTypes(newLists)) ;
                }
            } else {
                listKpiType.addAll(iCiKpiDao.getKpiTypes(newLists));
            }
            //组合数据，需要导入的数据和其他不修改的字段
            List<Kpi_Type> newList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                boolean flag = false;
                List ls = (List) list.get(i);
                for (int j = 0; j < listKpiType.size(); j++) {
                    Kpi_Type kt = listKpiType.get(j);
                    if(ls.get(0).equals(kt.getKpi_name())){
                        if (!"".equals(ls.get(4).toString())){
                            String classId = handleClass(ls.get(4).toString());
                            kt.setKpi_class_id(classId);
                        }
                        if (!"".equals(ls.get(5).toString())){
                            result +=handleKpiObj(ls.get(5).toString(),kt.getId());
                        }
                        if ("是".equals(ls.get(6).toString())){
                            kt.setIs_match(1);
                        }else {
                            kt.setIs_match(0);
                        }
                        if (!"".equals(ls.get(7).toString())){
                           kt.setMaximum(BigDecimal.valueOf(Double.valueOf(ls.get(7).toString())));
                        }
                        if (!"".equals(ls.get(8).toString())){
                            kt.setMinimum(BigDecimal.valueOf(Double.valueOf(ls.get(8).toString())));
                        }
                        kt.setKpi_name(ls.get(0).toString());
                        kt.setKpi_alias(ls.size()>=2?ls.get(1).toString():"");
                        kt.setKpi_desc(ls.size()>=3?ls.get(2).toString():"");
                        kt.setVal_unit(ls.size()>=4?ls.get(3).toString():"");
                        kt.setXgsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        kt.setDomain_id(domainId==null?"-1":domainId);
                        newList.add(kt);
                        flag = true;
                        break;
                    }
                }
                //添加新增的字段
                if(!flag){
                    Kpi_Type kt = new Kpi_Type();
                    String kpiTypeId = SeqUtil.getId();
                    if (!"".equals(ls.get(4).toString())){
                        String classId = handleClass(ls.get(4).toString());
                        kt.setKpi_class_id(classId);
                    }
                    kt.setId(kpiTypeId);
                    if (!"".equals(ls.get(5).toString())){
                        result +=handleKpiObj(ls.get(5).toString(),kpiTypeId);
                    }
                    kt.setKpi_name(ls.get(0).toString());
                    kt.setKpi_alias(ls.size()>=2?ls.get(1).toString():"");
                    kt.setKpi_desc(ls.size()>=3?ls.get(2).toString():"");
                    kt.setVal_unit(ls.size()>=4?ls.get(3).toString():"");
                    kt.setCjsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    kt.setCjr_id(TokenUtils.getTokenUserId());
                    kt.setYxbz("1");
                    if ("是".equals(ls.get(6).toString())){
                        kt.setIs_match(1);
                    }else {
                        kt.setIs_match(0);
                    }
                    if (!"".equals(ls.get(7).toString())){
                        kt.setMaximum(BigDecimal.valueOf(Double.valueOf(ls.get(7).toString())));
                    }
                    if (!"".equals(ls.get(8).toString())){
                        kt.setMinimum(BigDecimal.valueOf(Double.valueOf(ls.get(8).toString())));
                    }
                    kt.setDomain_id(domainId==null?"-1":domainId);
                    newList.add(kt);
                }
            }

            Integer sizes = newList.size();
            List<Kpi_Type> typeLists = new ArrayList<>();
            typeLists.addAll(newList);
            //判断是否有必要分批
            if (pointsDataLimit < sizes) {
                //分批数
                int part = sizes / pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    iCiKpiDao.deleteByKpiNames(typeLists.subList(0, pointsDataLimit));
                    typeLists.subList(0, pointsDataLimit).clear();
                }
                if (!newLists.isEmpty()) {
                    iCiKpiDao.deleteByKpiNames(typeLists);

                }
            } else {
                System.out.println("不需要分批,执行处理:" + typeLists);
                iCiKpiDao.deleteByKpiNames(typeLists);
            }
            //再添加
            iCiKpiDao.insertByKpiNames(newList);


            //获取字典  “指标单位” 数据
            List<Map<String, Object>> listDict = dictService.findDiceBySjIdHump("1000");

            List units = new ArrayList();
            List kpiUnits = new ArrayList();
            List dictName = new ArrayList();
            newList.forEach(kpi_type -> {
                kpiUnits.add(kpi_type.getVal_unit());
            });

            listDict.forEach(map -> {
                dictName.add(map.get("dictName"));
            });
            //差集
            units = (List) kpiUnits.stream().filter(t-> !dictName.contains(t)).collect(Collectors.toList());
            //筛选不为空的
            units = (List) units.stream().filter(ls -> !"".equals(ls)).collect(Collectors.toList());
            //去重
            units = (List) units.stream().distinct().collect(Collectors.toList());

            //组合参数
            List<Map> parms = new ArrayList<>();
            units.forEach( u -> {
                Map map = new HashMap();
                map.put("sjId", "1000");
                map.put("dictName", u);
                map.put("dictBm", u);
                parms.add(map);
            });
            //添加字典
            dictService.addDictHumps(JSONObject.toJSONString(parms));
            if (!"".equals(result)){
                pageResult.setReturnBoolean(true);
                pageResult.setReturnMessage(result);
                return pageResult;
            }else{
                pageResult.setReturnBoolean(true);
                pageResult.setReturnMessage("导入成功！");
                return pageResult;
            }
        }catch (Exception e){
            e.printStackTrace();
            PageResult.setMessage(false, "导入失败！");
            pageResult.setReturnBoolean(false);
            pageResult.setReturnMessage("导入失败！");
            return pageResult;
        }
    }

    /**
     * 处理指标导入时指标大类的处理
     * @param className
     * @return
     */
    public String handleClass(String className){
        String domainId =TokenUtils.getTokenOrgDomainId();
        IomCiKpiClass iomCiKpiClass = iomCiKpiClasService.findByName(className);
        if (iomCiKpiClass!=null){
            return iomCiKpiClass.getId();
        }else {
            IomCiKpiClass iomCiKpiClas = new IomCiKpiClass();
            String classId = SeqUtil.nextId() + "";
            iomCiKpiClas.setName(className);
            iomCiKpiClas.setId(classId);
            iomCiKpiClas.setYxbz(1);
            iomCiKpiClas.setDomain_id(domainId==null?"-1":domainId);
            iomCiKpiClassDao.insert(iomCiKpiClas);
            return classId;
        }
    }

    /**
     * 处理指标导入时对象组的处理
     * @param objs
     * @param kpiId
     * @return
     */
    public String handleKpiObj(String objs,String kpiId){
        //获取数据域ID
        String orgDomainId = TokenUtils.getTokenOrgDomainId();
        String result = "";
        //删除对象和指标值
        ciKpiTypeDao.deleteByKpiId(kpiId);
        List<String> listObj = new ArrayList<>();
        if (objs.contains(",")){
            listObj = Arrays.asList(objs.split(","));
        }else if(objs.contains("，")){
            listObj = Arrays.asList(objs.split("，"));
        }else if (objs!=null && !"".equals(objs)){
            listObj.add(objs);
        }
        for (String objName: listObj) {
            Type type = typeDao.findByMc(objName,orgDomainId);
            if (type!=null){
                //创建对象
                CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
                //id
                typeInfo.setId(SeqUtil.nextId() + "");
                //模型id
                typeInfo.setKpi_id(kpiId);
                //对象id
                typeInfo.setObj_id(type.getId());
                //对象类型
                typeInfo.setObj_type(2);
                //创建时间
                String time1 = DateUtils.getDate();
                typeInfo.setCjsj(time1);
                //有限标志
                typeInfo.setYxbz(1);
                //新增对象
                ciKpiTypeDao.insertInfo(typeInfo);
            }else {
                result += "没有此对象："+objName+",";
            }
        }
        return result;
    }
    /**
     * 删除单条记录
     *
     * @param id
     */
    @Override
    public int deleteInfo(String id) {
    	//对应redis缓存删除
        CiKpiInfo ciKpiInfo=iCiKpiDao.getInfo(id);
        if(ciKpiInfo!=null) {
        	String kpiName=ciKpiInfo.getKpi_name();
        	String key="kpi_"+kpiName;
        	if(RedisUtils.exists(key)) {
                RedisUtils.remove(key);
        	}
        }
        //删除对象关联之前同时删除redis对应数据
        List<String> ciTypeIdList=iCiKpiDao.getObjIdByKpiId(id);
        if(ciTypeIdList!=null && ciTypeIdList.size()>0) {
        	for(String ciTypeId:ciTypeIdList) {
        		String key="pmvCiKpiType_"+id+"_"+ciTypeId;
  			    if (RedisUtils.exists(key)) {
                    RedisUtils.remove(key);
  			    }
        	}
        }
        //删除模型
        int i = iCiKpiDao.deleteInfo(id);

        //删除对象
        ciKpiTypeDao.deleteByKpiId(id);
        //删除指标值
        ciKpiThresDao.deleteByKpiId(id);
        if (i > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<CiKpiInfo> selectKpiByCiTypeId(String ciTypeId) {
        return iCiKpiDao.selectKpiByCiTypeId(ciTypeId);
    }
    
    @Override
    public List<CiKpiInfo> selectKpiByCiTypeIdSort(String ciTypeId) {
        return iCiKpiDao.selectKpiByCiTypeIdSort(ciTypeId);
    }
    
    @Override
    public List<Map<String,Object>> selectKpiByCiTypeIdByPerformanceChart(String ciTypeId,String domainId) {
        return iCiKpiDao.selectKpiByCiTypeIdByPerformanceChart(ciTypeId,domainId);
    }
    
    @Override
    public List<CiKpiInfo> selectKpiByCiTypeIdByPaging(String ciTypeId,String pageNum,String pageSize) {
    	int pageNumInt=Integer.parseInt(pageNum);
    	int pageSizeInt=Integer.parseInt(pageSize);
        return iCiKpiDao.selectKpiByCiTypeIdByPaging(ciTypeId,pageNumInt,pageSizeInt);
    }
    
    @Override
    public List<Map<String,Object>> queryPerformanceCurveByCiId(String ciTypeId,String pageNum,String pageSize,String domainId,String kpiName) {
    	int pageNumInt=Integer.parseInt(pageNum);
    	int pageSizeInt=Integer.parseInt(pageSize);
        return iCiKpiDao.selectKpiByCiTypeIdByPagingNew(ciTypeId,(pageNumInt - 1) * pageSizeInt,pageSizeInt,domainId,kpiName);
    }
    
    @Override
    public int selectKpiByCiTypeIdByPagingCount(String ciTypeId,String domainId,String kpiName) {
        return iCiKpiDao.selectKpiByCiTypeIdByPagingCount(ciTypeId,domainId,kpiName);
    }

    @Override
    public CiKpiInfo findByNameKPI(String name) {
        return iCiKpiDao.findByNameKPI(name);
    }

    @Override
    public Map findByNameKPIClass(String name) {
    	List<Map> list = iCiKpiDao.findByNameKPIClass(name);
    	if (list!=null && list.size()>0) {
			return list.get(0);
		}
        return null;
    }

    @Override
    public Map findByNameKPIClassByZ(String name) {
        return iCiKpiDao.findByNameKPIClassByZ(name);
    }

    @Override
    public List<Map> findByListClassId(String ids) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        return iCiKpiDao.findByListClassId(ids,null);
    }

    @Override
    public String saveKpi(CiKpiInfo ciKpiInfo) {
        //查询是否存在KPI
        CiKpiInfo ciKpiInfo1 = iCiKpiDao.findByNameKPI(ciKpiInfo.getKpi_name());
        if (ciKpiInfo1 != null && ciKpiInfo1.getId() != null) {
            return ciKpiInfo1.getId();
        }
        String id = SeqUtil.nextId().toString();
        ciKpiInfo.setIs_match(0);
        ciKpiInfo.setId(id);
        ciKpiInfo.setCjsj(DateUtils.getDate());
        ciKpiInfo.setYxbz(1);
        ciKpiInfo.setCjr_id("0");
        iCiKpiDao.insertInfo(ciKpiInfo);
        return id;
    }
    
    @Override
    public String getIomCiKpiTypeByKpiIdAndCiTypeId(String kpiId,String ciTypeId) {
    	int i=iCiKpiDao.getIomCiKpiTypeByKpiIdAndCiTypeId(kpiId, ciTypeId);
    	if(i==0) {
    		//性能推送过来的数据要建立大类与KPI关系，往iom_ci_kpi_type表插入数据---2020-08-13
			//创建对象
            CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
            //id
            typeInfo.setId(SeqUtil.nextId() + "");
            //模型id
            typeInfo.setKpi_id(kpiId);
            //对象id
            typeInfo.setObj_id(ciTypeId);
            //对象类型
            typeInfo.setObj_type(2);
            //创建时间
            String time1 = DateUtils.getDate();
            typeInfo.setCjsj(time1);
            //有限标志
            typeInfo.setYxbz(1);
            List<CiKpiTypeInfo> list = new ArrayList<CiKpiTypeInfo>();
            list.add(typeInfo);
            ciKpiTypeDao.insertInfoList(list);
            return "1";
    	}
    	return "0";
    }
    
    @Override
    public String insertIomCiKpiTypeByKpiIdAndCiTypeId(Map<String,String> map) {
    	for (Map.Entry<String, String> m : map.entrySet()) {
    		String kpiId=m.getKey();
    		String ciTypeId=m.getValue();
       		//性能推送过来的数据要建立大类与KPI关系，往iom_ci_kpi_type表插入数据---2020-08-13
    			//创建对象
                CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
                //id
                typeInfo.setId(SeqUtil.nextId() + "");
                //模型id
                typeInfo.setKpi_id(kpiId);
                //对象id
                typeInfo.setObj_id(ciTypeId);
                //对象类型
                typeInfo.setObj_type(2);
                //创建时间
                String time1 = DateUtils.getDate();
                typeInfo.setCjsj(time1);
                //有限标志
                typeInfo.setYxbz(1);
                List<CiKpiTypeInfo> list = new ArrayList<CiKpiTypeInfo>();
                list.add(typeInfo);
                int i=ciKpiTypeDao.insertInfoList(list);
                if(i>0) {         	
    				try {
    					String key="pmvCiKpiType_"+kpiId+"_"+ciTypeId;
    					Map<String, Object> map1 = bean2map(typeInfo);
                        RedisUtils.set(key,map1,20*60*1000L);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
                }
        }
        return "1";
            
    	
    }
    
    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description: 把JavaBean转化为map
     */
	public static Map<String,Object> bean2map(Object bean) throws Exception{
	    Map<String,Object> map = new HashMap<>();
	    //获取JavaBean的描述器
	    BeanInfo b = Introspector.getBeanInfo(bean.getClass(),Object.class);
	    //获取属性描述器
	    PropertyDescriptor[] pds = b.getPropertyDescriptors();
	    //对属性迭代
	    for (PropertyDescriptor pd : pds) {
	        //属性名称
	        String propertyName = pd.getName();
	        //属性值,用getter方法获取
	        Method m = pd.getReadMethod();
            //用对象执行getter方法获得属性值
	        Object properValue = m.invoke(bean);

	        //把属性名-属性值 存到Map中
	        map.put(propertyName, properValue);
	    }
	    return map;
	}
	
    @Override
    public Map<String, Object> saveKpiByPmvSavePerformance(IomCiKpi iomCiKpi) {
        //数据域ID
        String domainId = null;
        //获取数据域对象
        Object domain = domainService.findDomain("default");
        if (domain != null){
            Map map = (Map) domain;
            domainId = map.get("domain_id").toString();
        }
    	Map<String,Object> map=new HashMap<String, Object>();
        //查询是否存在KPI
        CiKpiInfo ciKpiInfo1 = iCiKpiDao.findByNameKPI(iomCiKpi.getKpiName());
        if (ciKpiInfo1 != null && ciKpiInfo1.getId() != null) {
        	map.put("kpiId", ciKpiInfo1.getId());
            return map;
        }
        String id = SeqUtil.nextId().toString();
        iomCiKpi.setIsMatch(0);
        iomCiKpi.setId(id);
        iomCiKpi.setCjsj(new Date());
        iomCiKpi.setDomainId(domainId==null?-1:Integer.parseInt(domainId));
        iomCiKpi.setCjrId("4");
        iomCiKpiMapper.insert(iomCiKpi);
        CiKpiInfo ciKpiInfo2=iCiKpiDao.getInfo(id);
        try {
			map=convertBean(ciKpiInfo2);
			//性能推送过来的数据要建立大类与KPI关系，往iom_ci_kpi_type表插入数据---2020-08-13
			String ciTypeId=iomCiKpi.getTypeId();
			//创建对象
            CiKpiTypeInfo typeInfo = new CiKpiTypeInfo();
            //id
            typeInfo.setId(SeqUtil.nextId() + "");
            //模型id
            typeInfo.setKpi_id(id);
            //对象id
            typeInfo.setObj_id(ciTypeId);
            //对象类型
            typeInfo.setObj_type(2);
            //创建时间
            String time1 = DateUtils.getDate();
            typeInfo.setCjsj(time1);
            //有限标志
            typeInfo.setYxbz(1);
            List<CiKpiTypeInfo> list = new ArrayList<CiKpiTypeInfo>();
            list.add(typeInfo);
            ciKpiTypeDao.insertInfoList(list);
		} catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
        return map;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map<String,Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getCiKpiAll(String kpiName,String kpiClassId) {
        //获取数据域ID
        //String domainId = TokenUtils.getTokenOrgDomainId();
        return iCiKpiDao.getCiKpiAll(kpiName,null,kpiClassId);
    }

    @Override
    public int getKpi_TypeCount(String search, String isMatch) {
        //获取数据域ID
        String domainId = TokenUtils.getTokenDataDomainId();
        return iCiKpiDao.getKpi_TypeCount(search, isMatch,domainId);
    }

    @Override
    public Object findByKpiProperty(String propertyName, String kpiId) {
        return iCiKpiDao.findByKpiProperty(propertyName, kpiId);
    }

    @Override
    public List<String> findCIidList(String ciCode, String type, String valName, String typeValName) {
        List<String> cis = null;
        List<String> types = null;

        if (StringUtils.isNotEmpty(valName) && StringUtils.isNotEmpty(ciCode)) {
            cis = Arrays.asList(ciCode.split(","));
        }

        if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(typeValName)) {
            types = Arrays.asList(type.split(","));
        }
        return iCiKpiDao.findCIidList(cis, types, valName, typeValName);
    }

    @Override
    public List<Map<String, Object>> getKpiInfoByKpiIds(List<String> list) {
    	//获取数据域ID
        String domainId = TokenUtils.getTokenOrgDomainId();
        String [] kpiNameArray = list.toArray(new String[list.size()]);
		Map<String,Object> itemMap=new HashMap<String,Object>();
		if(kpiNameArray!=null && kpiNameArray.length>0) {
			itemMap.put("kpiNameList", kpiNameArray);
		}else {
			itemMap.put("kpiNameList", "");
		}
		if(domainId!=null && !"".equals(domainId)) {
			itemMap.put("domainId", domainId);
		}else {
			itemMap.put("domainId", "");
		}		
        return iCiKpiDao.getKpiInfoByKpiIds(itemMap);
    }
    
    @Override
    public List<Map<String, Object>> getKpiByKpiIds(Map<String,Object> itemMap) {

        return iCiKpiDao.getKpiByKpiIds(itemMap);
    }
    
    @Override
    public List<Map<String, Object>> getKpiByKpiNames(Map<String,Object> itemMap) {

        return iCiKpiDao.getKpiByKpiNames(itemMap);
    }
    
    @Override
    public List<String> getCiTypeInfoByKpiIds(Map<String,Object> itemMap) {

        return iCiKpiDao.getCiTypeInfoByKpiIds(itemMap);
    }

    @Override
    public List<Map<String, Object>> getKpiUnitByKpiIds(List<String> list) {

        return iCiKpiDao.getKpiUnitByKpiIds(list);
    }

    @Override
    public List<String> getCiCode(List<String> ciValue) {
        String domainId = TokenUtils.getTokenDataDomainId();
        List<String> resultList = new ArrayList<>();
        List<Map<String, String>> majorList = typeItemDao.getMajorList(domainId);
        for (Map<String, String> map: majorList) {
            List<String> list = iCiKpiDao.getCiCode(ciValue,map.get("mp_ci_item"),map.get("ci_type_id"));
            if (list!=null && list.size()>0){
                resultList.addAll(list);
            }
        }
        return resultList;
    }
    
    @Override
    public void initializeCiKpiType() {
		String keyPre = new StringBuilder().append("pmvCiKpiType").append("-").append("*").toString();
		int redisNum=RedisUtils.size(keyPre);
		List<Map<String,Object>> listData=iCiKpiDao.getIomCiKpiTypeInfos();
		if(redisNum!=listData.size()) {
			if(listData!=null && listData.size()>0) {
				for(Map<String,Object> map:listData) {
					String kpiId=(String) map.get("kpiId");
					String ciTypeId=(String) map.get("objId");
					String key="pmvCiKpiType_"+kpiId+"_"+ciTypeId;
					if (!RedisUtils.exists(key)) {
                        RedisUtils.set(key,map,20*60*1000L);
					}					
				}
			}
		}
	}
    
    @Override
    public void initializeCiKpiInfoToRedis() {
    	Map<String,Object> itemMap=new HashMap<String,Object>();
    	itemMap.put("kpiNameList", "");
		String keyPre = new StringBuilder().append("kpi").append("-").append("*").toString();
		int redisNum=RedisUtils.size(keyPre);
		List<Map<String,Object>> listData=iCiKpiDao.getCiKpiInfosByIds(itemMap);
		if(redisNum!=listData.size()) {
			if(listData!=null && listData.size()>0) {
				for(Map<String,Object> map:listData) {
					String kpiName=(String) map.get("kpiName");
					String key="kpi_"+kpiName;
					if (!RedisUtils.exists(key)) {
                        RedisUtils.set(key,map,20*60*1000L);
					}					
				}
			}
		}
	}

    @Override
    public List<Map<String, Object>> selAllKpiClass() {
        return iCiKpiDao.getAllKpiClass();
    }

    @Override
    public List<Map<String, Object>> findByListSource(String ids) {
        return iCiKpiDao.findByListSource(ids);
    }

    @Override
    public List<Map<String, Object>> findKpiIdsBySource(String source) {
        return iCiKpiDao.findKpiIdsBySource(source);
    }

    @Override
    public String findKpiIdBySource(String source) {
        return iCiKpiDao.findKpiIdBySource(source);
    }

    @Override
    public List<Map<String, Object>> findCisByClassName(String className) {
        return iCiKpiDao.findCisByClassName(className);
    }

    @Override
    public List<Map<String, Object>> findCisByType(String type, String typeVal) {
        return iCiKpiDao.findCisByType(type, typeVal);
    }

    @Override
    public List<Map<String, Object>> findKpiClassByClassId(List<String> name) {
        return iCiKpiDao.findKpiClassByClassId(name);
    }

    @Override
    public List<Map<String, Object>> findKpiByKpiName(List<String> name) {
        return iCiKpiDao.findKpiByKpiName(name);
    }

    @Override
    public String findCisByCiTypeId(String ciTypeId) {
        return iCiKpiDao.findCisByCiTypeId(ciTypeId);
    }

    @Override
    public String findCisByMap(Map<String, Object> m) {
        return iCiKpiDao.findCisByMap(m);
    }

	@Override
	public List<Map<String, Object>> getCiKpiInfosByIds(Map<String,Object> itemMap) {
		
		return iCiKpiDao.getCiKpiInfosByIds(itemMap);
	}


    @Override
    public void clearCiKpiInfoRedisCache() {
        String kpiKey = new StringBuilder().append("kpi").append("-").append("*").toString();
        RedisUtils.removePattern(kpiKey);
        initializeCiKpiInfoToRedis();
    }
}