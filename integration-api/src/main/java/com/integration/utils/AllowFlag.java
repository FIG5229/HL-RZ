package com.integration.utils;

/**
 * 授权状态 200已授权 420 未授权 421 已过期
 */
public enum AllowFlag {
    unauthorized(420),authorized(200),expired(421);

    private int code;

    AllowFlag(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
