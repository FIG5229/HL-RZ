package com.integration.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexpUtil
 * @Description //TODO
 * @Author zhangfeng
 * @Date 2021/4/13 14:17
 * @Version 1.0
 **/
public class RegexpUtil {

    public static boolean checkValidity (String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
