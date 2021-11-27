package com.integration.sgcc.sm;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Hex;

import java.util.Arrays;

/**
 * 本类主要提供国密SM3算法 使用样例，
 * 此样例包含国密SM3算法的加密方法的使用；
 *
 * *********注意事项*********
 *  1) 所使用的包名为com.sgitg.sgcc.sm.*;
 *  2) 此算法使用只能依赖bcprov-jdk16-1.46.jar,项目中不能同时含有bouncy castle的其他版本的jar，
 * 否则使用过程中算法运算会抛出异常
 */
public class SM3Util extends SM3Utils {

    private static final  String UTF_8 = "utf-8";

    /**
     * 国密SM3算法加密
     * @param plainText 需要进行加密的数据
     * @return 返回加密后hex编码字符
     */
    public final String SM3Digest(String plainText) {
        // 需要加密的数据转化为byte数组
        byte[] byteArray = Strings.toUTF8ByteArray(plainText);
        SG_SM3UpDate(byteArray);
        byte[] sg_SM3Final = null;
        try {
            sg_SM3Final = SG_SM3Final();
        } catch (Exception e) {
            throw new RuntimeException("国密SM3算法加密失败",e);
        }
        return Strings.fromByteArray(Hex.encode(sg_SM3Final));
    }

    /**
     * 判断源数据与加密数据是否一致
     * @param srcStr       原字符串
     * @param sm3HexString 16进制字符串
     * @return 校验结果
     * @explain 通过验证原数组和生成的hash数组是否为同一数组，验证2者是否为同一数据
     */
    public boolean verify(String srcStr, String sm3HexString) {
        boolean flag = false;
        try {
            byte[] srcData = srcStr.getBytes(UTF_8);
            byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
            byte[] newHash = hash(srcData);
            if (Arrays.equals(newHash, sm3Hash)) {
                flag = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("国密SM3算法比对失败",e);
        }
        return flag;
    }

    /**
     * 返回长度=32的byte数组
     * @param srcData
     * @return
     * @explain 生成对应的hash值
     */
    public byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    public static void main(String[] args){
        System.out.println("************************SM3摘要***************************");
        //需要加密数据
        String getTokenReq = "0da7bac52f3c1279590db8b1aabe750a17ddacd6e26d01fd2edea8c8cccef41630f19075b4b7c5be47dd766880ef27d8a70d4343a69f63695de1f280cc8915838182d5a9ce739db92e1745faa213f26341e1da575d507eb470a1440f584bce89371b86901756a7e1bbd4c2f33e5e416517d7a8438559af8b1aae4a5c1c78e00f679f4c9fc9cc16a22212ae61b20cf361fd8531e68fbdec929a9f05d5c19f323cd163f5c33c8f0f29724fa47d7b3ec1cedd3525fbd7250946c47ed05420e274acf0b385efc3068bc5eefa6bf4d8644cd5bdb69cb33defab16b51fc9d86c473919590714d32344b8d186034e84f80390abd67f6788c44b9f1868baeb4ee4556e17178019412535ac347616ea2009278369f1f4dedfd073b38e3a9571a18785cadac55a036a747c46f954a24319ae11af0ee96ea7e1080f906360bb0e2f3317b031ce636ea3becd70bbaf36d0348dd7a629e9f481aeeec9748fc8da6ee8ed007848c0cf6bd91d71ae4e54dea511db2650651619601552972VH7VZVNX57k5bm2O";
        //0da7bac52f3c1279590db8b1aabe750a17ddacd6e26d01fd2edea8c8cccef41630f19075b4b7c5be47dd766880ef27d8a70d4343a69f63695de1f280cc8915838182d5a9ce739db92e1745faa213f26341e1da575d507eb470a1440f584bce89371b86901756a7e1bbd4c2f33e5e416517d7a8438559af8b1aae4a5c1c78e00f679f4c9fc9cc16a22212ae61b20cf361fd8531e68fbdec929a9f05d5c19f323cd163f5c33c8f0f29724fa47d7b3ec1cedd3525fbd7250946c47ed05420e274acf0b385efc3068bc5eefa6bf4d8644cd5bdb69cb33defab16b51fc9d86c473919590714d32344b8d186034e84f80390abd67f6788c44b9f1868baeb4ee4556e17178019412535ac347616ea2009278369f1f4dedfd073b38e3a9571a18785cadac55a036a747c46f954a24319ae11af0ee96ea7e1080f906360bb0e2f3317b031ce636ea3becd70bbaf36d0348dd7a629e9f481aeeec9748fc8da6ee8ed007848c0cf6bd91d71ae4e54dea511db2650651619601552972VH7VZVNX57k5bm2O
        //0da7bac52f3c1279590db8b1aabe750a17ddacd6e26d01fd2edea8c8cccef41630f19075b4b7c5be47dd766880ef27d8a70d4343a69f63695de1f280cc8915838182d5a9ce739db92e1745faa213f26341e1da575d507eb470a1440f584bce89371b86901756a7e1bbd4c2f33e5e416517d7a8438559af8b1aae4a5c1c78e00f679f4c9fc9cc16a22212ae61b20cf361fd8531e68fbdec929a9f05d5c19f323cd163f5c33c8f0f29724fa47d7b3ec1cedd3525fbd7250946c47ed05420e274acf0b385efc3068bc5eefa6bf4d8644cd5bdb69cb33defab16b51fc9d86c473919590714d32344b8d186034e84f80390abd67f6788c44b9f1868baeb4ee4556e17178019412535ac347616ea2009278369f1f4dedfd073b38e3a9571a18785cadac55a036a747c46f954a24319ae11af0ee96ea7e1080f906360bb0e2f3317b031ce636ea3becd70bbaf36d0348dd7a629e9f481aeeec9748fc8da6ee8ed007848c0cf6bd91d71ae4e54dea511db2650651619601552972VH7VZVNX57k5bm2O
        SM3Util sm3Util = new SM3Util();
        //使用国密SM3算法进行加密
        String sm3Digest = sm3Util.SM3Digest(getTokenReq);
        System.out.println("国密SM3加密后为：" + sm3Digest);
        System.out.println("7b9b1f4e43889175405ace53ae0475b1".length());
        System.out.println("比较结果"+sm3Util.verify(getTokenReq,sm3Digest));
    }
}
