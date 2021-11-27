package com.integration.utils;

import com.alibaba.fastjson.JSONObject;
import com.integration.config.IomPlatformParam;
import com.integration.entity.LicenseSign;
import com.integration.entity.PageResult;
import com.integration.utils.token.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class LicenseUtil {

    static Logger logger = LoggerFactory.getLogger(LicenseUtil.class);

    /**
     * 定时自检，默认 每日凌晨1点执行，可在properties配置
     */
//    @Async
//    @Scheduled(cron = "${scheduled.license.cron:0 0 1 * * ?}")
//    public void task(){
//        check(null);
//    }

    public static void check(String clientLisenceSignStr){

        logger.info("自检开始");
        try {
            //获取服务器识别码，没有则生成
            //识别码根据服务器信息生成 aes加密

            //获取授权码

            //循环自检不需要检测激活时间
            checkLicense(clientLisenceSignStr,false);
        }catch (Exception e){
            //防止定时任务挂掉
            e.printStackTrace();
            System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
            logger.info(String.format("检测出现错误。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
        }
    }

    public static PageResult checkLicense(String clientLisenceSignStr){
        return checkLicense(clientLisenceSignStr, true);
    }

    private static PageResult checkLicense(String clientLisenceSignStr,boolean register){
        //无授权码
        if(clientLisenceSignStr == null){

            System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));

            logger.info(String.format("没有授权码。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
            return DataUtils.returnPr(false,"没有授权码！");
        }


        //有授权码 解析授权码
        LicenseSign licenseSign = LicenseSign.init(clientLisenceSignStr);
        if(licenseSign != null){

            logger.info("lisence校验开始：");

            //获取授权码，有则拆分授权码（识别码+校验码） 无则设置标识false
            logger.info("软件授权码校验开始：");

            //根据授权码 rsa加密与校验码对比
            if(!RSAUtil.verify(licenseSign.getAesAuth(), licenseSign.getAesAuthCheckCode())){
                logger.error("授权码校验失败！");
                System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
                logger.info(String.format("授权校验码验证失败。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
                return DataUtils.returnPr(false,"授权校验码验证失败！");
            }



            logger.info("软件授权码校验结束：");


            //根据识别码得到信息

            Map<String,Object> infoMap = licenseSign.serverinfo();

            Map<String,String> infoMapLocal = SerialNumberUtil.getAllSn();
            logger.info("服务器信息比对开始：");

            //授权码是否在有效期
            LocalDateTime now = LocalDateTime.now();
            now.plusDays(SerialNumberUtil.VALIDATEDAY);

            LocalDateTime registerTime = Instant.ofEpochMilli((Long)infoMap.get(SerialNumberUtil.REGISTERDATE)).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            LocalDateTime validateTime = registerTime.plusDays(SerialNumberUtil.VALIDATEDAY);

            if(register){
                if(now.isAfter(validateTime)){
                    System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
                    logger.info(String.format("授权码不在激活有效期内。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
                    return DataUtils.returnPr(false,"授权码不在激活有效期内！");
                }
                //当前时间必须大于注册时间

                if(LocalDateTime.now().isBefore(registerTime)){
                    System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
                    logger.info(String.format("当前时间小于授权码注册时间。检测结果 %s，%s,%s",System.getProperty(SerialNumberUtil.ALLOWFLAG),LocalDateTime.now(),registerTime));
                    return DataUtils.returnPr(false,"授权码不在有效期内！");
                }
            }



            //授权版本比对
            if(!checkVersion(IomPlatformParam.version,infoMap.get(SerialNumberUtil.AUTHVERSION).toString())){

                System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
                logger.info(String.format("授权版本比对失败。检测结果 %s,%s,%s",System.getProperty(SerialNumberUtil.ALLOWFLAG),IomPlatformParam.version,infoMap.get(SerialNumberUtil.AUTHVERSION).toString()));
                return DataUtils.returnPr(false,"授权版本比对失败！");

            }

            //对比 cpuid ip mac mainboard
            for (String proper: SerialNumberUtil.INFOPROPERS) {
                if(!infoMap.get(proper).equals(infoMapLocal.get(proper))){
                    System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
                    logger.info(String.format("服务器信息比对失败。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
                    return DataUtils.returnPr(false,"服务器信息比对失败！");
                }
            }

            logger.info("服务器信息比对结束：");

            logger.info("试用时间比对开始：");

            //对比 endDate
            DateTimeFormatter endDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate endDate = Instant.ofEpochMilli((Long)infoMap.get(SerialNumberUtil.ENDDATE)).atZone(ZoneOffset.ofHours(8)).toLocalDate();


            //与当前服务器时间比对
            if(endDate.isBefore(LocalDate.now())){
                System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.expired.getCode()));
                logger.info(String.format("授权已过期。检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
                return DataUtils.returnPr(false,"授权已过期！");
            }

            logger.info("试用时间比对结束：");

            logger.info("lisence校验结束：");

            System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.authorized.getCode()));


            logger.info(String.format("检测结果 %s",System.getProperty(SerialNumberUtil.ALLOWFLAG)));
            Object allowModuleO = infoMap.get(SerialNumberUtil.ALLOWMODULE);
            List<String> allowModuleList = new ArrayList<>();
            if(allowModuleO != null){
                allowModuleList = JSONObject.parseArray(allowModuleO.toString(), String.class);
            }
            System.setProperty(SerialNumberUtil.ALLOWMODULE, JSONObject.toJSONString(allowModuleList));
            return DataUtils.returnPr(true,"授权检测成功！");
        }else{
            System.setProperty(SerialNumberUtil.ALLOWFLAG, String.valueOf(AllowFlag.unauthorized.getCode()));
            return DataUtils.returnPr(false,"授权检测失败！");
        }
    }


    /**
     * 比较 授权版本与产品版本
     *
     * 只比较大版本
     * @param version 产品版本
     * @param authVersion 授权版本
     * @return
     */
    public static boolean checkVersion(String version,String authVersion){

        if(StringUtils.isNotEmpty(version) && StringUtils.isNotEmpty(authVersion) &&
         version.startsWith(authVersion+".")){
            return true;
        }

        return false;
    }

    public static String  createClientCode(){

        Map<String,String> infoMapLocal = SerialNumberUtil.getAllSn();

        //服务器版本信息
        infoMapLocal.put(SerialNumberUtil.VERSION, IomPlatformParam.version);

        infoMapLocal.put(SerialNumberUtil.REGISTERMAN, TokenUtils.getTokenUserName());

        logger.info("系统信息："+JSONObject.toJSONString(infoMapLocal));
        String infoStr = SignUtil.toSignString(infoMapLocal);

        return Base64.getEncoder().encodeToString(infoStr.getBytes());

    }



    public static Map<String, Object> decodeAuthCode(String clientLisenceSignStr){
        LicenseSign licenseSign = LicenseSign.init(clientLisenceSignStr);
        if(licenseSign == null){
            return Collections.EMPTY_MAP;
        }

        if(!RSAUtil.verify(licenseSign.getAesAuth(), licenseSign.getAesAuthCheckCode())){
            return Collections.EMPTY_MAP;
        }

        return licenseSign.serverinfo();
    }


    public static String createQRCode(String text){

        BufferedImage bufferedImage = QRCodeUtil.encodeQRCode(text);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", stream);
            return Base64.getEncoder().encodeToString(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        String clientLisenceSignStr = "QmU2TUJKZlZrQjFVMnVGZ21HTUNBb3NJZkMvYXVnWm50UXE4M3RXUU55OTJDb0RZSno3UlhwTFQ5MC84UDZTZlpUVW1LSWtkbkNZeUFQNDNpSWdRQVZxUlhyNUlmTGptNkFaSFA3aGlnT3FDM3JSRktrT3F5WUFHLzhGeDRDa2prRDVzNnZyQ0lDdDhMazExQWlKalByaE1Pb093K1d6eXMxR3VZb2hXdnRVPTExMTExMURLb2RFeUk3T2ZFZm90VitoOE91VmhlQUUyQUdibVFuWDd1RDU3U0lITHJ1MHAyTUNYbTZiWXJnZVBlSWJmVUFrcURiRlMzMnpUL1BqNHVtbmt3TTNmRld0TGZTM3FIdDdnZ3Zna0FoOXY0OXlhbllDcTR1WUdVREVzQ21PRXpIZmFJa1FFSldzc3JHdXJyL2pjMi9ITzNUOU5ueDNLMnkyVHV5N1p6UXhBVFNGRHEyT3ZSRzdCcDVuT2x1NVVyQmwyMk5JVjkrejhPOFpTUU9Bd2F0U3E4RXpEUnN4b2VWY1Zrc1NlYW45UDJaWEd3bnFVTVRUWWV2UC9WZkJNOTQ2REMrVVdmZU44U2tlTmJRY3RvL3lLQjBsZ1c3MDhPL0NscUdGemtobkVUSmlEdk1LVFlZQzJSOEhSQmtBaTZGYW53UkMzcEtSVEh1c01DWVVEb2Q5ZUZuSTZzbzUrbUt0aHJibFZueGlrZTJDVGtFeUJvV3ZDdmdqdFpKTW4rOVFYc3hmckpTMzlHMHJSVXYzMXdleUp0Ni81NlZEek5QQTY4NFRMNEhyUVVDV3VJbWF6V1NqOC9iaU5ldW5yVVZIR08zVU1IMHM5eXdUQ0VLd0NhOHlENjJyRHA3V3M1UmpRZ0hpTlFvVUtDSlVLUG5raGdMZVFocGxMUFhRL3p1MTExMTExRGpJM051Q3FOa1ZtOTJNdFkrV0JtRGsxWEszWndNTEsrQ0pQeUQzWmlGMzBDMnBKT1ZuMHl0M3FybWErNW9PaEpiWjR4TGE0YjYzNWZ4c3pBWEx1NXZWcS9QamN3QmNlRlMzQkhRRktZcTduVHpkbmRsb0JCRXl6dmJKRzRRT3RCbWJZU21yZTJZaUhJWlh6OHRtbkhVSkd0NHd0cG1GU3hkbWd6NW03YzJ3PQ==";
        RSAUtil.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLNbmKl9/gLn7Bef/xtUkshC1WyrLZLRpXCcFYR1gQi0isWsZBTicC4efBOkkNG3r+1ue0gvtuU/tjREFGf4Y7HaKHGb5tNCOlMNeNjM5YLRwLFqrUSsQyD4rj4eua1ltearr24R0HilnTvnQm6Z/UY0s21vdOUFQBPY0GNAa+0wIDAQAB";
        LicenseSign licenseSign = LicenseSign.init(clientLisenceSignStr);
        Map<String,Object> infoMap = licenseSign.serverinfo();
        Object allowModuleO = infoMap.get(SerialNumberUtil.ALLOWMODULE);
        List<String> allowModuleList = new ArrayList<>();
        if(allowModuleO != null){
            allowModuleList = JSONObject.parseArray(allowModuleO.toString(), String.class);
        }
        System.setProperty(SerialNumberUtil.ALLOWMODULE, JSONObject.toJSONString(allowModuleList));
    }
}
