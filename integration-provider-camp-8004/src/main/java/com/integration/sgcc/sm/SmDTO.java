package com.integration.sgcc.sm;

import java.io.Serializable;

/**
 * @author demo
 */
public class SmDTO implements Serializable {

    private static final long serialVersionUID = 7339537933403560176L;

    /**
     * 随机字符串
     */
    private String nonce;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 加密内容数据[ sm4算法]
     */
    private String secData;

    /**
     * 加密签名[ sm3算法]
     */
    private String sign;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSecData() {
        return secData;
    }

    public void setSecData(String secData) {
        this.secData = secData;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "SmDTO{" +
                "nonce='" + nonce + '\'' +
                ", timestamp=" + timestamp +
                ", secData='" + secData + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}