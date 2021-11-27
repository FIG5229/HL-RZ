package com.integration.utils;

import com.integration.entity.PageResult;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: integration
 * @description
 * @author: hlq
 * @create: 2020-03-10 13:42
 **/
public class StringUtil {

    //下划线
    private static final char UNDERLINE='_';
    //外界传入的页码
    private static final String pageNum = "pageNum";
    //外界传入的每页大小
    private static final String pageSize = "pageSize";


    /**
     * 下划线 转 驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(param.charAt(i));
            if (c == UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将对象里面的属性和值转化成Map对象
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object object) {
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            Class<?> clazz = object.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(object));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将list对象里面的属性和值转化成List<Map>对象
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static List<Map> listObjectToMap(Object object) {
        List objs =  ((ArrayList) object);
        List<Map> list = new ArrayList<>();
        try {
            objs.forEach( obj -> {
                Map<String, Object> map = new HashMap<String,Object>();
                Class<?> clazz = obj.getClass();
                for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        map.put(field.getName(), field.get(obj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                list.add(map);
            });
        }catch (Exception e){
            return null;
        }
        return list;
    }

    public static List<Map> listListTolistMap(List<List<String>> list) {
        if(list == null || list.size() <= 0){
            return null;
        }
        List<Map> listMap = new ArrayList<>();
        list.forEach(ls -> {
            Map map = new HashMap();
            for(int i = 0;i < ls.size();i++){
                map.put(i+"",ls.get(i));
            }
            listMap.add(map);
        });
        return listMap;
    }

    /**
     * 非数据库分页
     * @param list
     * @return
     */
    public static PageResult PageNoDataBase(List<Map> list) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object pageNumObj = request.getAttribute(pageNum);
        Object pageSizeObj = request.getAttribute(pageSize);
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        if (pageNumObj==null) {
            pageNumObj = request.getParameter(pageNum);
        }
        if (pageSizeObj==null) {
            pageSizeObj = request.getParameter(pageSize);
        }
        return getPageResult(list, pageNumObj, pageSizeObj);
    }

    public static PageResult PageNoDataBase(List<Map> list, Object pageNumObj, Object pageSizeObj) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        if (pageNumObj==null) {
            return null;
        }
        if (pageSizeObj==null) {
            return null;
        }
        return getPageResult(list, pageNumObj, pageSizeObj);
    }

    public static PageResult getPageResult(List<Map> list, Object pageNumObj, Object pageSizeObj ) {
        Integer pageNum = Integer.parseInt(String.valueOf(pageNumObj));
        Integer pageSize = Integer.parseInt(String.valueOf(pageSizeObj));

        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        if (!pageNum.equals(pageCount)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex, toIndex);

        PageResult pageResult = new PageResult();
        pageResult.setTotalPage(pageCount);
        pageResult.setTotalResult(count);
        pageResult.setCurrentPage(pageNum);
        pageResult.setReturnObject(pageList);
        pageResult.setReturnBoolean(true);
        pageResult.setReturnMessage("成功");
        return pageResult;
    }

    /**
     * 防止科学计数法
     * @param obj
     * @return
     */
    public static String unScientificCounting(Object obj){
        if(obj == null){
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(obj.toString());
        return bigDecimal.toPlainString();
    }
}
