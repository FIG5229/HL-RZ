package com.integration.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final Log logger = LogFactory.getLog(AESUtil.class);

    // "算法/模式/补码方式"
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String password_default = "hanlinkeji202002";

    /*****************************************************
     * AES加密
     *
     * @param content
     *            加密内容
     * @param key
     *            加密密码，由字母或数字组成 此方法使用AES-128-ECB加密模式，key需要为16位
     *            加密解密key必须相同，如：abcd1234abcd1234
     * @return 加密密文
     ****************************************************/

    public static String enCode(String content, String key) {
        if (key == null || "".equals(key)) {
            logger.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            logger.info("key长度不是16位！");
            return null;
        }
        try {
            byte[] raw = key.getBytes(); // 获得密码的字节数组
            SecretKeySpec skey = new SecretKeySpec(raw, "AES"); // 根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM); // 根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.ENCRYPT_MODE, skey); // 初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte[] byte_content = content.getBytes("utf-8"); // 获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] encode_content = cipher.doFinal(byte_content); // 密码器加密数据
            return Base64.getEncoder().encodeToString(encode_content); // 将加密后的数据转换为字符串返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*****************************************************
     * AES解密
     *
     * @param content
     *            加密密文
     * @param key
     *            加密密码,由字母或数字组成 此方法使用AES-128-ECB加密模式，key需要为16位 加密解密key必须相同
     * @return 解密明文
     ****************************************************/

    public static String deCode(String content, String key) {
        if (key == null || "".equals(key)) {
            logger.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            logger.info("key长度不是16位！");
            return null;
        }
        try {
            byte[] raw = key.getBytes(); // 获得密码的字节数组
            SecretKeySpec skey = new SecretKeySpec(raw, "AES"); // 根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM); // 根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.DECRYPT_MODE, skey); // 初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte[] encode_content = Base64.getDecoder().decode(content); // 把密文字符串转回密文字节数组
            byte[] byte_content = cipher.doFinal(encode_content); // 密码器解密数据
            return new String(byte_content, "utf-8"); // 将解密后的数据转换为字符串返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String enCode(String content) {
        return enCode(content, password_default);
    }

    public static String deCode(String content) {
        return deCode(content, password_default);
    }

    /*****************************************************
     * AES加密解密测试
     *
     * @param args
     * @return
     ****************************************************/

    public static void main(String[] args) {
        String content = "加密解密测试";
        logger.info("加密content：" + content);
        String key = "abcd1234abcd1234";
        logger.info("加密key：" + key);
        String enResult = enCode(content, key);
        logger.info("加密result：" + enResult);
        String deResult = deCode(enResult, key);
        logger.info("解密result：" + deResult);
    }
}