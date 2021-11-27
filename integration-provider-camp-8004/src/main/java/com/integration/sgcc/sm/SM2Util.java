package com.integration.sgcc.sm;

import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.Arrays;

/**
 * 本类主要提供国密SM2算法 使用样例，
 * 此样例包含国密SM2算法的加密、解密、签名、验签方法的使用；
 *
 * *********注意事项*********
 * 1) 所使用的包名为com.sgitg.sgcc.sm.*;
 * 2) 此算法使用只能依赖bcprov-jdk16-1.46.jar,项目中不能同时含有bouncy castle的其他版本的jar，
 * 		否则使用过程中算法运算会抛出异常
 * 3) 以下SM2Util针对String字符串进行算法操作，
 * 		若项目组需要进行文件加密操作请使用SM2Utils对外方法进行byte数组算法运算处理
 */
public class SM2Util extends SM2Utils {

    /**
     * 国密SM2算法加密方法
     * @param plainText 需要加密的数据
     * @param pubKey 加密所需要的hex编码公钥
     * @return 返回加密后hex编码字符串
     */
    public final String encryptBySM2(String plainText, String pubKey){
        //将需要加密的信息进行utf8编码转换为byte数组
        byte[] msg= Strings.toUTF8ByteArray(plainText);
        //hex编码的公钥进行转换为byte数组
        byte[] key = Hex.decode(pubKey);
        byte [] cipherByteArray = null;
        try {
            cipherByteArray = 	SG_SM2EncData(key,msg);
        } catch (IOException e) {
            throw new RuntimeException("国密SM2算法加密失败",e);
        }
        //将加密后的byte数组进行hex编码，以String字符形式输出
        return Strings.fromByteArray(Hex.encode(cipherByteArray));
    }

    /**
     * 国密SM2算法解密方法
     * @param cipherText 加密后的hex编码数据
     * @param priKey 解密需要的hex 编码的私钥
     * @return 加密后明文数据
     */
    public final String decryptBySM2(String cipherText, String priKey){
        //加密后的密文先进行hex解码为byte数组
        byte[] cipher = Hex.decode(cipherText);
        //私钥进行hex解码为byte数组
        byte [] prikey = Hex.decode(priKey);
        byte[] plainByteArray = null;
        try {
            plainByteArray = SG_SM2DecData(prikey, cipher);
        } catch (IOException e) {
            throw new RuntimeException("国密SM2算法解密失败",e);
        }
        //解密后进行字符转为字符串形式
        return Strings.fromUTF8ByteArray(plainByteArray);
    }

    /**
     * 国密SM2算法签名方法
     * @param plainText 需要进行签名的数据
     * @param priKey 签名需要的hex编码私钥
     * @param userID 用户的唯一标识
     * @return  返回签名后的hex编码的密文
     */
    public final String signBySM2(String plainText, String priKey, String userID){
        byte[] signatureByteArray  = null;
        try {
            signatureByteArray = SG_SM2Sign(Strings.toUTF8ByteArray(userID), Hex.decode(priKey), Strings.toUTF8ByteArray(plainText));
        } catch (IOException e) {
            throw new RuntimeException("国密SM2算法签名失败",e);
        }
        return Strings.fromByteArray(Hex.encode(signatureByteArray));
    }

    /**
     * 国密SM2算法验签方法
     * @param cipherText 签名后的hex编码密文
     * @param plainText 签名之前的明文
     * @param pubKey 验签需要的hex编码的公钥
     * @param userID 用户的唯一标识
     * @return 返回验签的结果
     */
    public final  boolean verifyBySM2(String cipherText, String plainText, String pubKey, String userID){
        boolean verifySignByteArray = false;
        try {
            verifySignByteArray  = SG_SM2VerifySign(Strings.toUTF8ByteArray(userID), Hex.decode(pubKey), Strings.toUTF8ByteArray(plainText), Hex.decode(cipherText));
        } catch (IOException e) {
            throw new RuntimeException("国密SM2算法验签失败",e);
        }
        return verifySignByteArray;
    }




    public static void main(String[] args) {
        System.out.println("************************SM2加密/解密***************************");
        //需要加密的数据；
        String plainText = "国密SM2加解密测试数据";
        SM2Util sm2Util = new SM2Util();
        /**
         * 调用生成公钥私钥方法，生成hex编码公钥和私钥；
         * 以下为生成秘钥代码，可以使用此方法生成项目所需要的秘钥对；
         * 将公钥的放到前段/客户端，供SM2加密使用；
         * 将私钥以参数方式配置到配置文件中，注意：不能使用硬编码方式使用私钥！
         * 私钥可能涉及二次加密情况，具体情况请参考各自项目安全要求。
         */
        byte[] keyPair = sm2Util.SG_generateKeyPair();
        String priKeyHex= Strings.fromByteArray(Hex.encode(Arrays.copyOfRange(keyPair, 0, 32)));
        priKeyHex = "a0e914d434f01de3af490fda7924bdf1a35b278343e8c016a8844993738cf89c";
        System.out.println("随机生成的私钥为：" + priKeyHex);
        String pubKeyHex = Strings.fromByteArray(Hex.encode(Arrays.copyOfRange(keyPair, 32, 97)));
        pubKeyHex = "043da2c62ce1f29b0a3764cdfd77c8ea768159d204ec29bd317bcb262288d23a6ac4a408b706b4f443f960b4e8593b3a8f098433391f1a5297fbaaf051092fc86b";
        System.out.println("随机生成的公钥为：" + pubKeyHex);
        // 通过生成的公钥进行加密
        String encryptBySM2 = sm2Util.encryptBySM2(plainText, pubKeyHex);
        System.out.println("SM2算法加密后为: " + encryptBySM2);
        //使用私钥对加密后的密文解密
        String decryptBySM2 = sm2Util.decryptBySM2(encryptBySM2, priKeyHex);
        System.out.println("SM2算法解密后为: " + decryptBySM2);
        System.out.println("");



        //以下为国密SM2签名验签的方法使用实例
        System.out.println("************************SM2签名/验签***************************");
        String plaintext = "SM2签名验签测试";
        // 国密规范测试用户ID
        String userID = "PH2017.com";
        byte[] keyPair1 = sm2Util.SG_generateKeyPair();
        String priKey= Strings.fromByteArray(Hex.encode(Arrays.copyOfRange(keyPair1, 0, 32)));
        System.out.println("签名测试随机生成的私钥为：" + priKeyHex);
        String pubKey = Strings.fromByteArray(Hex.encode(Arrays.copyOfRange(keyPair1, 32, 97)));
        System.out.println("签名测试随机生成的公钥为：" + pubKeyHex);
        //签名方法的调用
        String signBySM2 = sm2Util.signBySM2(plaintext, priKey, userID);
        System.out.println("国密SM2签名后的密文为 : " + signBySM2);
        //验签方法的调用
        boolean verifyBySM2 = sm2Util.verifyBySM2(signBySM2, plaintext, pubKey, userID);
        System.out.println("国密SM2算法验签结果为： " +verifyBySM2 );
    }



}
