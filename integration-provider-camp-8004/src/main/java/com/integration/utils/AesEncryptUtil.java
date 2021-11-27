package com.integration.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
* @Package: com.integration.utils
* @ClassName: AesEncryptUtil
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 加密工具类
*/
public class AesEncryptUtil {
	private final static Logger logger = LoggerFactory.getLogger(AesEncryptUtil.class);
	 /**
	  * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
	  */
    private final static String KEY = "1234567890123456";
    private final static String IV = "1234567890123456";
     
     
    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            //"算法/模式/补码方式"NoPadding PkcsPadding
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
 
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8.name());
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
 
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
 
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8.name()), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8.name()));
 
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
 
            return new Base64().encodeToString(encrypted);
 
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
 
    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8.name()), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8.name()));
 
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original,StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
     
    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }
     
    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }
}
