package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.*;
import com.integration.feign.CzryService;
import com.integration.service.CiInfoToInterfaceService;
import com.integration.service.InfoService;
import com.integration.service.TypeItemService;
import com.integration.service.TypeService;
import com.integration.utils.ConstantUtil;
import com.integration.utils.ImportExeclUtil;
import com.integration.utils.RedisUtils;
import com.integration.utils.RegexpUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
* @Package: com.integration.controller
* @ClassName: TypeItemController
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 类定义
*/
@RestController
@RequestMapping("/typeItem")
public class TypeItemController {

    @Autowired
    private TypeItemService typeItemService;

    @Autowired
    private InfoService infoService;

    @Autowired
    private TypeService typeServie;
    
    @Autowired
    private CzryService czryService;
    @Autowired
    private CiInfoToInterfaceService ciInfoToInterfaceService;
    /**
     * 根据大类ID获取字段列表
     *
     * @return
     */
    @RequestMapping(value = "/findItemByTid", method = RequestMethod.GET)
    public List<TypeItem> findItemByTid(String tid) {
        return typeItemService.findItemByTid(tid);
    }
    
    /**
     * 根据大类ID获取字段列表(驼峰)
     *
     * @return
     */
    @RequestMapping(value = "/findItemByTidToHump", method = RequestMethod.GET)
    public List<Map<String,Object>> findItemByTidToHump(String tid) {
        return typeItemService.findItemByTidToHump(tid);
    }
    
    /**
     * 根据多个大类ID获取字段列表(驼峰)
     *
     * @return
     */
    @RequestMapping(value = "/findItemByTidsToHump", method = RequestMethod.POST)
    public List<Map<String,Object>> findItemByTidsToHump(String tid) {
        return typeItemService.findItemByTidsToHump(tid);
    }

    /**
     * 根据大类ID删除所有字段以及字段数据
     *
     * @return
     */
    @RequestMapping(value = "/deleteItemByAllByTid", method = RequestMethod.PUT)
    public boolean deleteItemByAllByTid(String tid) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("tid", tid);
        List<TypeItem> list = typeItemService.findItemByTid(tid);
        Integer i = 0;
        // Integer j=0;
        Map<String, Object> dataMap = typeItemService.findDataNoPage(map);
        Integer total = (Integer) dataMap.get("total");
        if (total != 0) {
            PageResult.fail("大类下有数据不能直接删除!");
            return false;
        }
        if (list != null && list.size() > 0) {
            for (TypeItem typeItem : list) {
                typeItem.setYxbz(0);
            }

            i = typeItemService.deleteItemByAllByTid(list);
        }
        if (i > 0) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.success("全部删除成功");
        } else {
            PageResult.fail("全部删除失败");
        }

        return i > 0;

    }

    /**
     * 根据大类ID获取字段数据
     */
    @RequestMapping(value = "/findData", method = RequestMethod.GET)
    public Map findData(@RequestParam Map map) {
        return typeItemService.findData(map);

    }
    
    /**
     * 根据大类ID获取字段数据(不分页)
     */
    @RequestMapping(value = "/findDataNoPage", method = RequestMethod.GET)
    public Map findDataNoPage(@RequestParam Map map) {
    	return typeItemService.findDataNoPage(map);
    }


    /**
     * 根据大类ID全量删除
     */
    @RequestMapping(value = "/deleteDataAll", method = RequestMethod.PUT)
    public boolean deleteDataAll(@RequestParam Map map) {
        Integer i = 0;
        i = typeItemService.deleteDataAll(map);

        if (i > 0) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.success("全部删除成功");
        } else {
            PageResult.fail("全部删除失败");
        }

        return i > 0;

    }

    /**
     * 根据大类ID获取字段和数据
     */
    @RequestMapping(value = "/findCiInfo", method = RequestMethod.GET)
    public Map findCiInfo(@RequestParam Map map) {
        return typeItemService.getCiInfo(map);
    }

    /**
     * 根据CI_ID集合获取字段和数据
     *
     * 将ciId修改为ciCode
     * @Author ztl
     */
    @RequestMapping(value = "/findCiList", method = RequestMethod.GET)
    public List findCiList(@RequestParam(value="ciIdList",required = false) String ciIdList,@RequestParam(value = "ciCodeList",required = false) String ciCodeList) {
        if (ciIdList!=null && !"".equals(ciIdList)){
            ciCodeList = typeItemService.getCiCodeList(ciIdList);
        }
        List<String> list = Arrays.asList(ciCodeList.split(","));
        return typeItemService.findCiListNoDomain(list);
    }
    
    @RequestMapping(value = "/findRequestCiList", method = RequestMethod.POST)
    public List findRequestCiList(@RequestParam Map<String,Object> itemMap) {
    	String ciCodes =(String) itemMap.get("ciCodes");
        List<String> list = Arrays.asList(ciCodes.split(","));
        return typeItemService.findCiListNoDomain(list);
    }
    
    /**
     * 根据CI_ID集合获取字段和数据(视图配置查询)
     * 将ciId修改为ciCode
     *  @Author ztl
     */
    @RequestMapping(value = "/findCiListByDiagram", method = RequestMethod.POST)
    public List findCiListByDiagram(@RequestParam("ciIdList") String ciIdList,@RequestParam("ciCodeList") String ciCodeList) {
        List<String> list = Arrays.asList(ciCodeList.split(","));
        return typeItemService.findCiListNoDomain(list);
    }
    
    /**
     * 根据CI_ID集合获取字段和数据(8009查询维度使用)
     */
    @RequestMapping(value = "/findCiListByDimension", method = RequestMethod.GET)
    public List findCiListByDimension(@RequestParam String ciIdList) {
        List<String> list = Arrays.asList(ciIdList.split(","));
        return typeItemService.findCiListByDimension(list);
    }

    /**
     * importExcelItem 新增字段
     *
     * @return
     */
    @OptionLog(desc = "新增字段", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public TypeItem addItem(TypeItem typeItem, HttpServletRequest request) {
        typeItem.setCjr_id(TokenUtils.getTokenUserId());
        boolean sf = typeItemService.itemNameExists(typeItem.getAttr_name(), typeItem.getCi_type_id());
        if (sf) {
            PageResult.fail("添加失败");
            return null;
        }

        if(StringUtils.isNotBlank(typeItem.getRegexp())){
            try {
                Pattern.compile(typeItem.getRegexp());
                if(StringUtils.isNotBlank(typeItem.getDef_val())){
                    boolean def_val_valid = RegexpUtil.checkValidity(typeItem.getDef_val(), typeItem.getRegexp());
                    if(!def_val_valid){
                        PageResult.fail("添加失败,默认值有误！");
                        return null;
                    }
                }
            }catch (PatternSyntaxException e){
                PageResult.fail("添加失败,正则表达式有误！");
                return null;
            }
        }

        TypeItem result = typeItemService.addItem(typeItem);

        if (result != null) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            //增加修改CI数据方法
            infoService.updateCiInfoName(typeItem.getCi_type_id());
            PageResult.success("添加成功");
        } else {
            PageResult.fail("添加失败");
        }

        return result;

    }

    /**
     * 修改字段
     *
     * @return
     */
    @OptionLog(desc = "修改字段", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    public Boolean updateItem(TypeItem typeItem, HttpServletRequest request) {
        TypeItem item = typeItemService.findTypeItem(typeItem.getId());
        if (!item.getAttr_name().equals(typeItem.getAttr_name())) {
            boolean sf = typeItemService.itemNameExists(typeItem.getAttr_name(), typeItem.getCi_type_id());
            if (sf) {
                PageResult.fail("字段已存在!");
                return null;
            }
        }
        typeItem.setXgr_id(TokenUtils.getTokenUserId());
        // 1.根据ci_type_id查询is_major=1的记录；
        List<TypeItem> list = typeItemService.findItemByTid(typeItem.getCi_type_id());
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIs_major() == 1) {
                count++;
            }
        }
        boolean flag = false;
        if (count == 0) {
            // 2.没有记录，走修改接口
            flag = typeItemService.updateItem(typeItem);
        } else {
            // 3.有记录，根据typeItem里的id查询is_major的值，
            // 如果typeItem的is_major和查到的值相同，走修改接口
            if (item.getIs_major() == typeItem.getIs_major()) {
                flag = typeItemService.updateItem(typeItem);
            } else {
                PageResult.fail("主键不能修改");
                return null;
            }

        }
        if (flag) {
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            //增加修改CI数据方法
            infoService.updateCiInfoName(typeItem.getCi_type_id());
            PageResult.success("修改成功");
        } else {
            PageResult.fail("修改失败");
        }

        return flag;
    }

    /**
     * 删除字段
     *
     * @return
     */
    @OptionLog(desc = "删除字段", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
    public boolean deleteItem(String id) {
        TypeItem typeItem = typeItemService.findTypeItem(id);
        boolean flag = typeItemService.deleteItem(id);
        if (flag) {
            //增加修改CI数据方法
            infoService.updateCiInfoName(typeItem.getCi_type_id());
            //修改CI版本
            ciInfoToInterfaceService.updateCiVesion();
            PageResult.success("删除成功");
        } else {
            PageResult.fail("删除失败");
        }
        return flag;
    }

    /**
     * 根据ID获取字段信息
     */
    @RequestMapping(value = "/findTypeItem", method = RequestMethod.GET)
    public TypeItem findTypeItem(String id) {
        return typeItemService.findTypeItem(id);
    }

    /**
     * 判断字段是否存在
     */
    @OptionLog(desc = "判断字段是否存在", module = "大类模块", writeParam = true, writeResult = true)
    @RequestMapping(value = "/itemNameExists", method = RequestMethod.GET)
    public boolean itemNameExists(String attr_name, String ci_type_id) {
        return typeItemService.itemNameExists(attr_name, ci_type_id);

    }

    /**
     * excel数据导出字段
     *
     * @throws IOException
     */
    @OptionLog(desc = "excel数据导出字段", module = "大类模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/exportExcelItem", method = RequestMethod.GET)
    public void exportExcelItem(@RequestParam List<String> tid, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        XSSFWorkbook workbook2007 = new XSSFWorkbook();
        typeItemService.exportExcelItem(workbook, workbook2007, tid, response);
        workbook.write(response.getOutputStream());
    }

    /**
     * excel数据导入
     *
     * @param ci_type_id
     * @param request
     * @return
     * @throws IOException
     */
    @OptionLog(desc = "excel数据导入", module = "大类模块", writeParam = false, writeResult = false)
    @RequestMapping(value = "/importExcelItem", method = RequestMethod.POST)
    public Object importExcelData(String ci_type_id, MultipartFile file, HttpServletRequest request)
            throws IOException {
        String cjr = TokenUtils.getTokenUserId();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        boolean isExcel2003 = "xls".equals(suffix);

        InputStream is = file.getInputStream();

        Type type = typeServie.findTypeById(ci_type_id);

        // 获取sheet页与大类名称相同的数据
        List<List<String>> listList = ImportExeclUtil.readType(is, isExcel2003, type);
        if (listList == null) {
            PageResult.fail("无匹配Sheet页!");
            return null;
        }
        int count = 0;

        List<TypeItem> list = new ArrayList();

        for (int i = 0; i < listList.get(0).size(); i++) {

            TypeItem typeItem = new TypeItem();
            if (i == 0 && typeItemService.findPK(ci_type_id) == null) {
                typeItem.setIs_requ(1);
                typeItem.setIs_major(1);
            }
            typeItem.setAttr_name(listList.get(0).get(i));
            typeItem.setCi_type_id(ci_type_id);
            typeItem.setCjr_id(cjr);
            typeItem.setAttr_type("字符串");
            typeItem.setSort(i+1);
            if (!typeItemService.itemNameExists(typeItem.getAttr_name(), typeItem.getCi_type_id())) {
                // 如果字段不重复
                list.add(typeItem);
            }

        }

        List<TypeItem> oldItemList = typeItemService.findItemByTid(ci_type_id);
        int allColumn = list.size();
        if(oldItemList != null){
            allColumn += oldItemList.size();
        }
        if(allColumn >199){
            PageResult.fail("导入失败，类定义总数不能多于199!");
            return null;
        }

        for (TypeItem typeItem : list) {
            typeItemService.addItem(typeItem);
            count++;
        }
        //修改CI版本
        ciInfoToInterfaceService.updateCiVesion();
        PageResult.success("导入成功!");
        return null;
    }

    @RequestMapping(value = "/itemimpor", method = RequestMethod.GET)
    public void findDfileList(HttpServletRequest request) {
        typeItemService.findDfileList(request);
    }

    @RequestMapping(value = "/dataimpor", method = RequestMethod.GET)
    public void findDfileData(HttpServletRequest request) {
        String cjr = TokenUtils.getTokenUserId();
        typeItemService.findDfileData(cjr);
    }

    /**
     * 根据大类id获取告警所用CI信息
     */
    @RequestMapping(value = "/getAlarmCiInfo", method = RequestMethod.GET)
    public List getAlarmCiInfo(@RequestParam Map map) {
        return typeItemService.getAlarmCiInfo(map);
    }

    /**
     * 根据configid获取告警所用CI信息
     */
    @RequestMapping(value = "/getCiInfoByConfigId", method = RequestMethod.GET)
    public List getCiInfoByConfigId(@RequestParam Map map) {
        return typeItemService.getCiInfoByConfigId(map);

    }

    /**
     * 根据大类ID获取CI编码
     *
     * @param tid
     * @return
     */
    @RequestMapping(value = "/findDataIdByTid", method = RequestMethod.POST)
    public List<Info> findDataIdByTid(String tid) {
        return infoService.findDataIdByTid(tid);
    }

    /**
     * 根据大类ID获取CI信息
     *
     * @param tid
     * @return
     */
    @RequestMapping(value = "/findCiInfoByTid", method = RequestMethod.GET)
    public List<Info> findCiInfoByTid(String tid) {
        return infoService.findDataIdByTid(tid);
    }

    /**
     * 根据ci获取ciinfo
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByBM", method = RequestMethod.GET)
    public List<Map> findCiInfoByBM(@RequestParam String ciBm) {
        List<Map> dataList  = infoService.findCiInfoByBM(ciBm);
        // 输出构建好的菜单数据。
        return dataList;

    }

    @RequestMapping(value = "/findAllCiInfoByBM", method = RequestMethod.GET)
    public JSONObject findAllCiInfoByBM(@RequestParam String ciBm) {
        return typeItemService.getCiItemByBm(ciBm);
    }

    /**
     * 根据ci获取ciinfo
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findRedisCiInfoByBM", method = RequestMethod.GET)
    public Map findRedisCiInfoByBM(@RequestParam String ciBm) {
    	String key = "ciInfo_"+ciBm;
    	if (RedisUtils.exists(key)) {
			return (Map) RedisUtils.get(key);
		}
    	Map map = null;
    	List<Map> dataList = infoService.findCiInfoByBM(ciBm);
    	if (dataList!=null && dataList.size()>0) {
    		map = dataList.get(0);
            RedisUtils.set(key,map,300000L);
		}
        // 输出构建好的菜单数据。
        return map;

    }
    
    /**
     * 根据ci获取ciinfo(8009推送性能)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByCiBM", method = RequestMethod.GET)
    public Map findCiInfoByCiBM(@RequestParam String ciBm) {
    	String key = "ciInfo_"+ciBm;
    	Map map = null;
    	List<Map> dataList = infoService.findCiInfoByBM(ciBm);
    	if (dataList!=null && dataList.size()>0) {
    		map = dataList.get(0);
    		RedisUtils.set(key,map,300000L);
		}
        // 输出构建好的菜单数据。
        return map;

    }
    
    /**
     * 根据ci获取ciinfo(8009推送性能)
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByCiBMTest", method = RequestMethod.GET)
    public Map findCiInfoByCiBMTest(@RequestParam Map map) {
    	String ciBm=(String) map.get("ciBm");
    	String key = "ciInfo_"+ciBm;
    	Map map1 = null;
    	List<Map> dataList = infoService.findCiInfoByBM(ciBm);
    	if (dataList!=null && dataList.size()>0) {
    		map1 = dataList.get(0);
    		RedisUtils.set(key,map1,300000L);
		}
        // 输出构建好的菜单数据。
        return map1;

    }
    
    /**
     * 根据ci获取ciinfo
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findCiInfoByCiBm", method = RequestMethod.GET)
    public Map findCiInfoByCiBm(String ciBm) {
    	List<Map> dataList = infoService.findCiInfoByBM(ciBm);
    	Map map=new HashMap<>();
    	if(dataList!=null && dataList.size()>0) {
    		map=dataList.get(0);
    		return map;
    	}
        return map;

    }

    /**
     * @Method findDataByTid
     * @Author sgh
     * @Version 1.0
     * @Description 根据大类Id和CiId获取数据
     * @Return com.integration.entity.PageResult
     * @Exception
     * @Date 2019/10/22
     * @Param [map]
     */
    @RequestMapping("findDataByTid")
    public TypeData findDataByTid(@RequestParam Map map) {
        return typeItemService.findDataByTid(map);
    }


    /**
     * @Method findByIdNum
     * @Author sgh
     * @Version 1.0
     * @Description 根据id查询数量（对外接口，判断是否存在）
     * @Return int
     * @Exception
     * @Date 2019/11/5
     * @Param [id]
     */
    @RequestMapping("findByIdNum")
    public Integer findByIdNum(@RequestParam("ciName") String ciName) {
        if(StringUtils.isEmpty(ciName)){
            return 0;
        }
        return infoService.findByIdNum(ciName);
    }
    
    /**
               * 根据ciBm获取ciIds
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getCiIdsByCiBm", method = RequestMethod.GET)
    public List<String> getCiIdsByCiBm(@RequestParam String ciBm){ 	
    	return infoService.getCiIdsByCiBm(ciBm);
    }
    
    /**
     * 查询大类所有的ci属性值
     * @param ciTypeId
     * @return
     */
    @GetMapping("/selectCiAttrs")
    public List<Map<String, Object>> selectCiAttrs(String ciTypeId) {
    	if (StringUtils.isNotEmpty(ciTypeId)) {
    		return typeItemService.selectCiAttrs(ciTypeId);
		}
    	return null;
	}
    
    /**
     * 查询机房列表信息
     * @return
     */
    @GetMapping("/selectJiFangListAll")
    public List<Map<String, Object>> selectJiFangListAll(String danwei,String jifang) {
		List<Map<String, Object>> reslist = selectJiFangList();
		List<Map<String, Object>> returnList = reslist;
		if (reslist!=null && reslist.size()>0) {
			for (Map<String, Object> map1 : reslist) {
				int num1 = 0;
				List<Map<String, Object>> list1 = (List<Map<String, Object>>) map1.get("child");
				if (list1!=null && list1.size()>0) {
					for (Map<String, Object> map2 : list1) {
						int num2 = 0;
						List<Map<String, Object>> list2 = (List<Map<String, Object>>) map2.get("child");
						if (list2!=null && list2.size()>0) {
							num2 = list2.size();
						}
						num1 += num2;
						map2.put("quyuNum", num2);
					}
				}
				map1.put("quyuNum", num1);
			}
			
			
			
			//以级别小的为准
			if (StringUtils.isNotEmpty(jifang)) {
				for (Map<String, Object> danweiMap : reslist) {
					List<Map<String, Object>> jifangList = (List<Map<String, Object>>) danweiMap.get("child");
					if (jifangList!=null && jifangList.size()>0) {
						for (Map<String, Object> jifangMap : jifangList) {
							if (jifang.equals(jifangMap.get("主键"))) {
								returnList = (List<Map<String, Object>>) jifangMap.get("child");
								break;
							}
						}
					}
				}
			}else if (StringUtils.isNotEmpty(danwei)) {
				for (Map<String, Object> danweiMap : reslist) {
					if (danwei.equals(danweiMap.get("主键"))) {
						returnList = (List<Map<String, Object>>) danweiMap.get("child");
						break;
					}
				}
			}
		}
		clearChild(returnList);
		return returnList;
	}
    
    @GetMapping("/selectJiFangList")
    public List<Map<String, Object>> selectJiFangList() {
		Map map = czryService.findCzryById(TokenUtils.getTokenUserId());
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("crList");
		List<String> list2 = new ArrayList<String>();
		for (Map<String, Object> map1 : list) {
			String roleName = (String) map1.get("roleName");
			list2.add(roleName);
		}
		List<Map<String, Object>> reslist = typeItemService.selectJiFangList(ConstantUtil.COMPUTERROOMTYPENAME,
				ConstantUtil.COMPUTERROOMARERTYPENAME, list2);
		return reslist;
	}
    
    private void clearChild(List<Map<String, Object>> list) {
    	if (list!=null && list.size()>0) {
    		for (Map<String, Object> map : list) {
    			List<Map<String, Object>> child = (List<Map<String, Object>>) map.get("child");
    			if (child!=null) {
    				map.put("child", child.size());
				}else {
					map.put("child", 0);
				}
    		}
		}
	}
    
    /**
     * 查询机房列表信息
     * @return
     */
    @GetMapping("/findComputerRoomInfo")
    public List<Map<String, Object>> findComputerRoomInfo(String computerRoom,String computerRoomArea,String userId,String computerRoomBuilding) {
    	Map map=czryService.findCzryById(userId);
    	List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("crList");
    	List<String> list2=new ArrayList<String>();
    	for(Map<String, Object> map1:list) {
    		String roleName=(String) map1.get("roleName");
    		list2.add(roleName);
    	}
    	if (StringUtils.isNotEmpty(computerRoom) && StringUtils.isNotEmpty(computerRoomArea)) {
    		return typeItemService.findComputerRoomInfo(computerRoom,computerRoomArea,list2,computerRoomBuilding);
		}
    	return null;
	}

    /**
     * 查询CIID所有的ci属性值
     * @param ciTypeId
     * @return
     */
    @RequestMapping("/selectCiAttrsById")
    public List<Map<String, Object>> selectCiAttrsById(String ciTypeId) {
        if (StringUtils.isNotEmpty(ciTypeId)) {
            return typeItemService.selectCiAttrsById(ciTypeId);
        }
        return null;
    }

    /**
     * 根据大类ID获取CI编码
     *
     * @param tid
     * @return
     */
    @RequestMapping(value = "/findDataIdByTidList", method = RequestMethod.GET)
    public List<String> findDataIdByTidList(String tid) {
        return typeItemService.findDataIdByTidList(tid);
    }


    /**
     * 根据大类ID获取CI信息
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/findCiInfoByTids", method = RequestMethod.POST)
    public PageResult findCiInfoByTids(@RequestBody Map<String,Object> map) {
        if (map == null || map.get("ciIds")==null) {
            return null;
        }
        String tids = map.get("ciIds").toString();
        Integer pageSize = Integer.parseInt(map.get("pageSize").toString());
        Integer pageNum = Integer.parseInt(map.get("pageNum").toString());
        PageInfo pageInfo = infoService.findDataIdByTids(tids,pageSize,pageNum);
        PageResult pageResult = new PageResult();
        pageResult.setReturnBoolean(true);
        pageResult.setReturnObject(pageInfo);
        pageResult.setTotalResult((int) pageInfo.getTotal());
        return pageResult;
    }
    /**
     * 根据大类ID获取字段列表及对应的字段数据
     *
     * @return
     */
    @RequestMapping(value = "/findItemAndDataByTid", method = RequestMethod.GET)
    public List<TypeItem> findItemAndDataByTid(String tid,String ciId,String ciCode) {
        return typeItemService.findItemAndDataByTid(tid,ciId,ciCode);
    }

    /**
     * 查询安全态势感知对应的场站及坐标
     * @param tid
     * @return
     */
    @RequestMapping(value = "/getSecurityCiInfo", method = RequestMethod.GET)
    public List<Map<String,String>> getSecurityCiInfo(String tid){
        return typeItemService.getSecurityCiInfo(tid);
    }

    /**
     * 安全态势感知系统
     * 根据id和模糊条件查询CI信息
     * @param tid 大类ID
     * @param vague 模糊条件
     * @return
     */
    @RequestMapping(value = "/getSecurityCiInfoByTidAndVague",method = RequestMethod.GET)
    public Map<String,String> getSecurityCiInfoByTidAndVague(@RequestParam String tid,@RequestParam String vague){
        return typeItemService.getSecurityCiInfoByTidAndVague(tid,vague);
    }
    /**
     * 根据大类ID获取字段列表
     *
     * @return
     */
    @RequestMapping(value = "/updateTypeItemList", method = RequestMethod.POST)
    public boolean updateTypeItem(String typeItemStr) {

        return typeItemService.updateTypeItem(typeItemStr);
    }

    /**
     * 根据名称属性查询对应字段
     *
     * @param cIType
     * @param cIName
     * @return
     */
    @RequestMapping(value = "/findMpCiItemByIdTypeId", method = RequestMethod.GET)
    public String findMpCiItemByIdTypeId(@RequestParam String cIType, String cIName) {
        return typeItemService.findMpCiItemByIdTypeId(cIType,cIName);
    }
}
