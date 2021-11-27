package com.integration.sgcc.sm;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author demo
 */
public class SM2Result {
    public BigInteger r;
    public BigInteger s;
    public BigInteger R;
    public byte[] sa;
    public byte[] sb;
    public byte[] s1;
    public byte[] s2;
    public ECPoint keyra;
    public ECPoint keyrb;

    public SM2Result() {
    }
}
