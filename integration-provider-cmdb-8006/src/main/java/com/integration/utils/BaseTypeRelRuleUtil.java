package com.integration.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
* @Package: com.integration.utils
* @ClassName: BaseTypeRelRuleUtil
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description:  配置报表大类关系查询工具类
*/
public abstract class BaseTypeRelRuleUtil {
	/**
	 * 作为唯一标识的id
	 */

	private static final String ID = "IUAGASJBKNKJS";
	private List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
	String sourceKey = null;
	String targetKey = null;
	/**
	 * 找到的所有关系
	 */
	JSONArray resList = new JSONArray();
	/**
	 * 找到的所有节点id
	 */
	List<String> resIds = new ArrayList<String>();

	int count = 99999;

	public BaseTypeRelRuleUtil(String sourceKey, String targetKey) {
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
	}

	public BaseTypeRelRuleUtil(String sourceKey, String targetKey, Integer count) {
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
		if (count != null && count > 0) {
			this.count = count;
		}
	}

	/**
	 * 循环执行的找下级方法
	 * @param ids 要查出的ids
	 * @return
	 */
	public abstract Object getDate(List<String> ids);

	/**
	 * 获取关系
	 * @param id
	 * @return
	 */
	public Map<String, Object> getData(String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		return getData(ids);
	}

	/**
	 * 获取关系
	 * @param ids
	 * @return
	 */
	public Map<String, Object> getData(List<String> ids) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (ids!=null && ids.size()>0) {
			getRel(ids,ids);
		}
		for (int i = 0; i < resList.size(); i++) {
			JSONObject one = resList.getJSONObject(i);
			String sourceTypeId=(String) one.get("startTypeId");
			String relId=(String) one.get("rltId");
			String targetTypeId=(String) one.get("endTypeId");
			one.put(ID, sourceTypeId+relId+targetTypeId);
		}
		res.put("ids", this.resIds);
		res.put("rels", this.resList);
		return res;
	}

	/**
	 * 获取所有路径
	 * @param startIds
	 * @param endIds
	 * @return
	 */
	public List<Map<String, Object>> getPath(List<String> startIds,List<String> endIds) {
		getData(startIds);
		for (String startId : startIds) {
			for (String endId : endIds) {
				List<String> oldIdsT = new ArrayList<String>();
				oldIdsT.add(startId);
				getRelData(startId, endId, oldIdsT,new JSONArray());
			}
		}

		return this.res;
	}


	/**
	 * 拔出ids和rels
	 * @param list
	 * @return
	 */
	public Map<String, Object> dataFormat(List<Map<String, Object>> list) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();
		JSONArray rels = new JSONArray();
		for (Map<String, Object> one : list) {
			List<String> idsT = (List<String>) one.get("ids");
			JSONArray relsT = (JSONArray) one.get("rels");
			ids.addAll(idsT);
			rels.addAll(relsT);
		}

		ids = ids.stream().distinct().collect(Collectors.toList());
		rels = distinctJSONArray(rels);
		res.put("ids", ids);
		res.put("rels", rels);
		return res;
	}



	/**
	 * 去重jsonArray
	 * @param arr
	 * @return
	 */
	public static JSONArray distinctJSONArray(JSONArray arr) {
		JSONArray resArray = new JSONArray();
		for (Object arrObject : arr) {
			boolean flag = true;
			JSONObject oldJsonObject = (JSONObject) arrObject;
			for (Object resObject : resArray) {
				JSONObject newJsonObject = (JSONObject) resObject;
				String newType=(String) newJsonObject.get(ID);
				String oldType=(String) oldJsonObject.get(ID);
				if (newType.equals(oldType)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				resArray.add(oldJsonObject);
			}
		}

		for (int i = 0; i < resArray.size(); i++) {
			JSONObject one = resArray.getJSONObject(i);
			one.remove(ID);
		}
		return resArray;
	}

	/**
	 * 获取关联节点和线
	 *
	 * @return ids是找到所有节点的id集合， 是找到所有节点的关系集合
	 */
	private void getRel(List<String> ids,List<String> sjIds) {
		if (count > 0) {
			count--;
			resIds.addAll(ids);
			resIds = resIds.stream().distinct().collect(Collectors.toList());
			Object obj = getDate(ids);
			if (obj != null) {
				JSONArray data = JSON.parseArray(JSON.toJSONString(obj));
				if (data != null && data.size() > 0) {
					List<String> idTemps = new ArrayList<String>();
					for (Object object : data) {
						JSONObject one = (JSONObject) object;
						//去除下级指回去的情况---2020-11-26
						Object endTypeId=one.get("endTypeId");
						String endTypeIdStr=endTypeId.toString();
						boolean flag=sjIds.contains(endTypeIdStr);
						if(flag) {
							continue;
						}
						Object sourceObj = one.get(sourceKey);
						Object targetObj = one.get(targetKey);
						if (sourceObj != null && targetObj != null) {
							String sourceId = sourceObj.toString();
							String targetId = targetObj.toString();
							idTemps.add(sourceId);
							idTemps.add(targetId);
							resList.add(one);
						}
					}

					idTemps = idTemps.stream().distinct().collect(Collectors.toList());

					// 去掉上次便利过的
					List<String> tempIds2 = new ArrayList<String>();
					for (String tempId : idTemps) {
						boolean flag = true;
						for (String relId : resIds) {
							if (tempId.equals(relId)) {
								flag = false;
								break;
							}
						}
						if (flag && tempIds2!=null) {
							tempIds2.add(tempId);
						}
					}

					resIds.addAll(tempIds2);
					if (tempIds2!=null && tempIds2.size()>0) {
						getRel(tempIds2,ids);
					}
				}
			}
		}

	}




	public void getRelData(String startId, String endId,List<String> oldIdsT,JSONArray oldRelsT) {
		for (int i = 0; i < this.resList.size(); i++) {
			JSONArray oldRels = JSON.parseArray(JSON.toJSONString(oldRelsT));
			List<String> oldIds = JSON.parseArray(JSON.toJSONString(oldIdsT), String.class);
			JSONObject one = this.resList.getJSONObject(i);
			if (one != null) {
				Object sourceObj = one.get(sourceKey);
				Object targetObj = one.get(targetKey);
				if (sourceObj != null && targetObj != null) {
					String sourceId = sourceObj.toString();
					String targetId = targetObj.toString();
					String tempId = null;
					if (startId.equals(sourceId)) {
						tempId = targetId;
					} else if (startId.equals(targetId)) {
						tempId = sourceId;
					}

					if (tempId != null) {
						boolean flag = true;
						if (oldRels!=null && oldRels.size()>0) {
							for (Object object : oldRels) {
								JSONObject old = (JSONObject) object;
								if (old.getInteger(ID).equals(one.getInteger(ID))) {
									flag = false;
									break;
								}
							}
						}
						if (flag) {
							oldRels.add(one);
							oldIds.add(tempId);
							if (endId.equals(tempId)) {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("rels", oldRels);
								map.put("ids", oldIds);
								res.add(map);
							}else {
								getRelData(tempId, endId,oldIds,oldRels);
							}
						}
					}
				}
			}
		}
	}
}
