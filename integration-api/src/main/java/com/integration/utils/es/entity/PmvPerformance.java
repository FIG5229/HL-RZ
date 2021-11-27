package com.integration.utils.es.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.integration.utils.HumpLineUtil;

import lombok.Data;

/**
 * pmv es对应解析类
 * iom_pmv_performance
 * @author Dell
 *
 */
@Data
public class PmvPerformance {
	private String val;
	private String cjsj;
	private Long occTimeNum;
	private String occTime;
	private String ciTypeName;
	private String kpiId;
	private String kpiName;
	private String otherContent;
	private Integer dataDource;
	private String ciTypeId;
	private String ciName;
	private String ciId;
	
	//统计值
	private Double maxVal;
	private Double minVal;
	private Double avgVal;
	
	/**
	 * list获取对象
	 * @param list
	 * @return
	 */
	public static List<PmvPerformance> parse(List<Map<String, Object>> list){
		List<PmvPerformance> pmvPerformance = new ArrayList<PmvPerformance>();
		for (Map<String, Object> map : list) {
			pmvPerformance.add(parse(map));
		}
		return pmvPerformance;
	}
	
	/**
	 * map获取对象
	 * @param map
	 * @return
	 */
	public static PmvPerformance parse(Map<String, Object> map){
		JSONObject jsonObject = new JSONObject();
		for (String key : map.keySet()) {
			jsonObject.put(HumpLineUtil.lineToHump(key), map.get(key));
		}
		return jsonObject.toJavaObject(PmvPerformance.class);
	}
	
	/**
	 * 获取与es对应的map
	 * @return
	 */
	public Map<String, Object> toLineMap() {
		Map<String, Object> res = new HashMap<String, Object>();
		Class<? extends PmvPerformance> cls = this.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            Field f = fields[i];  
            f.setAccessible(true);
            try {
				res.put(HumpLineUtil.humpToLine(f.getName()), f.get(this));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }   
        //这仨是统计值，用于接收参数
        res.remove("max_val");
        res.remove("min_val");
        res.remove("avg_val");
		return res;
	}
}
