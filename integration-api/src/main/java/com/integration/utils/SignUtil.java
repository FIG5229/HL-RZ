package com.integration.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUtil {

    private static final Logger log = LoggerFactory.getLogger(SignUtil.class);

    /**
     * 分隔符 分割授权码的两部分授权信息、校验码
     */
    public static final String SPLIT = "111111";

    public static String toSignString(Map<String, String> infoMap) {
        if (infoMap == null || infoMap.keySet().size() == 0) {
            return "";
        }
        String[] keyArr = infoMap.keySet().toArray(new String[1]);
        Arrays.sort(keyArr);
        StringBuffer sb = new StringBuffer();

        for (String key : keyArr) {
            sb.append(key).append("=").append(infoMap.get(key)).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static Map<String, String> stringToMap(String signStr) {

        Map<String, String> infoMap = new HashMap<>();

        if (signStr == null) {
            return infoMap;
        }

        String[] infoProperArr = signStr.split("&");

        for (String infoProper : infoProperArr) {
            String[] infoProperItemArr = infoProper.split("=");
            infoMap.put(infoProperItemArr[0], infoProperItemArr[1]);
        }

        return infoMap;
    }






    public static void main(String[] args) {
        log.debug(toSignString(SerialNumberUtil.getAllSn()));
    }

}
