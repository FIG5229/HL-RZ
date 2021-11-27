package com.integration.sgcc.sm;

/**
 * @author demo
 */
public class SM4_Context {
    public int mode = 1;
    public long[] sk = new long[32];
    public boolean isPadding = true;

    protected SM4_Context() {
    }
}
