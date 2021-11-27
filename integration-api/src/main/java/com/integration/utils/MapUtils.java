package com.integration.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * map的工具类
 *
 * @author suozhaoyu
 * @version 0.0.1
 * @since 0.0.1
 */
public class MapUtils {

    /**
     * 获取map key的值，如果获取不到则使用defaultValue
     *
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getObjValue(Map<?, ?> map, Object key, Object defaultValue) {
        Optional<Map<?, ?>> opt = Optional.ofNullable(map);
        Optional<Object> optValue = opt.map(m -> m.get(key));
        return optValue.orElse(defaultValue);
    }

    /**
     * 获取map key的值，如果获取不到则使用defaultValue
     *
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringValue(Map<?, String> map, Object key, String defaultValue) {
        Object returnValue = getObjValue(map, key, defaultValue);
        String returnStr = (String) returnValue;
        if (StringUtils.isBlank(returnStr)) {
            returnStr = defaultValue;
        }
        return returnStr;
    }

    /**
     * 获取map key的值，如果获取不到则使用defaultValue
     *
     * @param map
     * @param key
     * @param
     * @return
     */
    public static BigDecimal getBigDecimalValue(Map<?, ?> map, Object key, BigDecimal defaultValue) {
        Object returnValue = getObjValue(map, key, defaultValue);
        return new BigDecimal(returnValue.toString());
    }

    /**
     * 将Map中的key由下划线转换为驼峰
     *
     * @param map
     * @return
     */
    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = toFormatCol(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    public static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }
}
