package com.integration.controller;

import com.alibaba.fastjson.JSONObject;
import com.integration.aop.log.annotations.OptionLog;
import com.integration.entity.Dict;
import com.integration.entity.Dicts;
import com.integration.entity.PageResult;
import com.integration.generator.dao.IomCampDictMapper;
import com.integration.generator.entity.IomCampDict;
import com.integration.generator.entity.IomCampDictExample;
import com.integration.rabbit.Sender;
import com.integration.service.DictService;
import com.integration.utils.DataUtils;
import com.integration.utils.RedisUtils;
import com.integration.utils.StringUtil;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典
 *
 * @author dell
 */
@RestController
public class DictController {

	@Autowired
	private DictService dictService;

	@Autowired
	private Sender sender;
	/**
	 * 新增字典
	 *
	 * @param dict
	 * @param request
	 * @return
	 */
	@OptionLog(desc = "新增字典", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/addDict", method = RequestMethod.POST)
	public Object addDict(Dict dict, HttpServletRequest request) {
		dict.setCjr_id(TokenUtils.getTokenUserId());
		Dict result = dictService.addDict(dict);
		if (dict.getSj_id()!=null && "258888".equals(dict.getSj_id())){
			sender.sendDatasourceChgNotice();
		}
		PageResult.setMessage("添加成功!", true);
		return result;
	}

	/**
	 * 新增字典  驼峰命名
	 * @param dict
	 * @param request
	 * @return
	 */
	@OptionLog(desc = "新增字典", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/addDictHump", method = RequestMethod.POST)
	public Object addDictHump(Dicts dict, HttpServletRequest request) {
		Dicts result = dictService.addDictHump(dict);
		PageResult.setMessage("添加成功!", true);
		return result;
	}

	/**
	 * 新增字典  驼峰命名
	 * @param dicts
	 * @param request
	 * @return
	 */
	@OptionLog(desc = "新增字典", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/addDictHumps", method = RequestMethod.POST)
	public Object addDictHump(String dicts, HttpServletRequest request) {
		boolean bool = dictService.addDictHump(JSONObject.parseArray(dicts, Map.class));
		PageResult.setMessage("添加成功!", true);
		return bool;
	}

	/**
	 * 删除字典
	 *
	 * @param dict_id
	 * @return
	 */
	@OptionLog(desc = "删除参数", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/deleteDict", method = RequestMethod.POST)
	public Object deleteDict(String dict_id) {
		Dict dict = dictService.findDictById(dict_id);
		if (dict != null) {
			List<Map<String, Object>> childrens = dictService.findDiceBySj_id(dict.getDict_bm());
			if (childrens == null || childrens.size() == 0) {
				PageResult.setMessage("删除成功!", true);
				int result = dictService.deleteDict(dict_id);
				if ("258888".equals(dict.getSj_id())|| "258888".equals(dict.getDict_bm())){
					sender.sendDatasourceChgNotice();
				}
				return result;
			} else {
				PageResult.setMessage("字典类型需要先删除内容后才能删除！", false);
				return null;
			}
		} else {
			PageResult.setMessage("该字典不存在！", false);
			return null;
		}
	}

	/**
	 * 修改字典
	 *
	 * @param dict
	 * @param request
	 * @return
	 */
	@OptionLog(desc = "修改参数", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/updateDict", method = RequestMethod.POST)
	public Map<String, Object> updateDict(Dict dict, HttpServletRequest request) {
		dict.setXgr_id(TokenUtils.getTokenUserId());
		Map<String,Object> result = dictService.updateDict(dict);
		return result;

	}

	/**
	 * 批量删除字典
	 *
	 * @param dictIdList
	 * @return
	 */
	@OptionLog(desc = "批量删除字典", module = "字典模块", writeParam = true, writeResult = true)
	@RequestMapping(value = "/deleteDictList", method = RequestMethod.POST)
	public Object deleteDictList(String dictIdList) {
		String[] list = dictIdList.split(",");
		PageResult.setMessage("删除成功!", true);
		return dictService.deleteDictList(list);
	}

	/**
	 * 根据名称模糊查询字典列表
	 *
	 * @param dict_name
	 * @return
	 */
	@RequestMapping(value = "/findDict", method = RequestMethod.POST)
	public List<Dict> findDict(String dict_name) {
		return dictService.findDict(dict_name);
	}

	/**
	 * 查询新增字典编码是否已经存在
	 *
	 * @param dict_bm
	 * @return
	 */
	@RequestMapping(value = "/existsBm", method = RequestMethod.POST)
	public PageResult findDict(String dict_bm, String sj_id, String dict_name) {

		int count = dictService.existsDict_bm(dict_bm, sj_id, dict_name);
		if (count == 0) {
			return DataUtils.returnPr(false);
		} else if (count == 1) {
			return DataUtils.returnPr(true, "编码和名字都存在");
		} else if (count == 2) {
			return DataUtils.returnPr(true, "编码存在");
		} else if (count == 3) {
			return DataUtils.returnPr(true, "名字存在");
		}
		return DataUtils.returnPr(true, "查询失败");
	}

	/**
	 * 查询字典ID获取字典信息
	 *
	 * @param dict_id
	 * @return
	 */
	@RequestMapping(value = "/findDictById", method = RequestMethod.GET)
	public Dict findDictById(String dict_id) {
			return dictService.findDictById(dict_id);
	}

	/**
	 * 查询上级ID获取字典信息
	 *
	 * @param sj_id
	 * @return
	 */
	@RequestMapping(value = "/findDiceBySj_id", method = RequestMethod.GET)
	public List<Map<String, Object>> findDiceBySj_id(@RequestParam String sj_id) {
			return dictService.findDiceBySj_id(sj_id);
	}

	/**
	 * 根据上级模糊查询ID查询字典
	 *
	 * @return
	 */
	@RequestMapping(value = "/getDiceBySjId", method = RequestMethod.GET)
	public List<Dict> findParentDict(@RequestParam(name = "sj_id", required = true) String sj_id,
			@RequestParam(name = "dict_name", required = false) String dict_name) {
			return dictService.findParentDict(sj_id, dict_name);
	}

	/**
	 * 根据上级模糊查询ID查询字典
	 *
	 * @return
	 */
	@RequestMapping(value = "/findParentDictByBm", method = RequestMethod.GET)
	public List<Dict> findParentDictByBm(@RequestParam(name = "sj_id", required = true) String sj_id,
			@RequestParam(name = "dict_name", required = false) String dict_name) {
			return dictService.findParentDictByBm(sj_id, dict_name);
	}

	/**
	 * 根据上级ID查询字典（从redis中获取）
	 *
	 * @return
	 */
	@RequestMapping(value = "/findDiceBySjId", method = RequestMethod.GET)
	public Object findParentDictForRedis(String sj_id,String dict_name) {
		return dictService.findParentDictByBm(sj_id, dict_name);
	}

	/**
	 * 根据上级ID查询字典（驼峰命名）
	 *
	 * @return
	 */
	@RequestMapping(value = "/findDiceBySjIdHump", method = RequestMethod.GET)
	public Object findDiceBySjIdHump(String sjId) {
		List<Map<String, Object>> listMap = new ArrayList<>();
		dictService.findDiceBySj_id(sjId).forEach(map -> {
			Map map1 = new HashMap();
			map.forEach((k,v) -> {
				map1.put(StringUtil.underlineToCamel(k), v);
			});
			listMap.add(map1);
		});
		return listMap;
	}

	@Resource
	private IomCampDictMapper iomCampDictMapper;

	@GetMapping("/findDictByDictbm")
	public IomCampDict findDictByDictbm(String dict_bm) {
		if (StringUtils.isNotEmpty(dict_bm)) {
			IomCampDictExample iomCampDictExample = new IomCampDictExample();
			com.integration.generator.entity.IomCampDictExample.Criteria criteria = iomCampDictExample.createCriteria();
			criteria.andYxbzEqualTo(1);
			criteria.andDictBmEqualTo(dict_bm);
			List<IomCampDict> iomCampDicts = iomCampDictMapper.selectByExample(iomCampDictExample);
			if (iomCampDicts!=null && iomCampDicts.size()>0) {
				return iomCampDicts.get(0);
			}
		}
		return null;
	}
	/**
	 * @Author: ztl
	 * date: 2021-08-11
	 * @description: 根据上级ID和dictBm获取单条字典数据
	 */
	@RequestMapping(value = "/getDictBySjIdDictBm", method = RequestMethod.GET)
	public Dict getDictBySjIdDictBm(String sjId,String dictBm) {
		if (StringUtils.isNotEmpty(sjId)&& StringUtils.isNotEmpty(dictBm)) {
			List<Dict> iomCampDictList = iomCampDictMapper.getDictBySjIdDictBm(sjId,dictBm);
			if (iomCampDictList!=null && iomCampDictList.size()>0) {
				return iomCampDictList.get(0);
			}
		}
		return null;
	}
}
