package com.integration.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.integration.utils.es.service.Pmv.UNDERLINE;

public class HumpLineUtil {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern linePattern = Pattern.compile("(_)(\\w)");

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        //str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(2).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static Map<String, Object> humpToLineMap(Map<String, Object> mapParam) {

        Map<String, Object> resultMap = new HashMap<>();

        for (String key : mapParam.keySet()) {

            Object value = mapParam.get(key);//取出值

            //把key转为下划线

            String newKey = humpToLine(key);
            resultMap.remove(key);
            resultMap.put(newKey, value);

        }

        return resultMap;

    }

//    public static void main(String[] args) {
//        String str = "p_arentUuid";
//        str = lineToHump(str);
//    }

    /**
     * 将对象转成TreeMap,属性名为key,属性值为value
     * @param object    对象
     * @return
     * @throws IllegalAccessException
     */
    public static TreeMap<String, Object> objToMap(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        while ( null != clazz.getSuperclass() ) {
            Field[] declaredFields1 = clazz.getDeclaredFields();
            for (Field field : declaredFields1) {
                String name = lineToHump(field.getName());
                // 获取原来的访问控制权限
                boolean accessFlag = field.isAccessible();
                // 修改访问控制权限
                field.setAccessible(true);
                Object value = field.get(object);
                // 恢复访问控制权限
                field.setAccessible(accessFlag);
                if (null != value && StringUtils.isNotBlank(value.toString())) {
                    //如果是List,将List转换为json字符串
                    if (value instanceof List) {
                        value = JSON.toJSONString(value);
                    }
                    treeMap.put(name, value);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return treeMap;
    }

    /**
     * 把map的key转换成驼峰命名
     * @param map
     * @return
     */
    public static Map<String, Object> toReplaceKeyLow(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var2.next();
                re_map.put(underlineToCamel((String) entry.getKey()), map.get(entry.getKey()));
            }

            map.clear();
        }

        return re_map;
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(param.charAt(i)));
            }
        }
        return sb.toString();
    }

}