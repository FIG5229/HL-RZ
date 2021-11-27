package com.integration.sgcc.sm;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author demo
 */
public class Cipher {
    private int ct = 1;
    private ECPoint p2;
    private SM3Utils sm3keybase;
    private SM3Utils sm3c3;
    private byte[] key = new byte[32];
    private byte keyOff = 0;

    protected Cipher() {
    }

    private void Reset() {
        this.sm3keybase = new SM3Utils();
        this.sm3c3 = new SM3Utils();
        byte[] p = Util.byteConvert32Bytes(this.p2.getX().toBigInteger());
        this.sm3keybase.update(p, p.length);
        this.sm3c3.update(p, p.length);
        p = Util.byteConvert32Bytes(this.p2.getY().toBigInteger());
        this.sm3keybase.update(p, p.length);
        this.ct = 1;
        this.NextKey();
    }

    private void NextKey() {
        SM3Utils sm3keycur = new SM3Utils(this.sm3keybase);
        sm3keycur.update((byte)(this.ct >> 24 & 255));
        sm3keycur.update((byte)(this.ct >> 16 & 255));
        sm3keycur.update((byte)(this.ct >> 8 & 255));
        sm3keycur.update((byte)(this.ct & 255));
        sm3keycur.doFinal(this.key);
        this.keyOff = 0;
        ++this.ct;
    }

    protected ECPoint Init_enc(SM2 sm2, ECPoint userKey) {
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters)key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters)key.getPublic();
        BigInteger k = ecpriv.getD();
        ECPoint c1 = ecpub.getQ();
        this.p2 = userKey.multiply(k);
        this.Reset();
        return c1;
    }

    protected void Encrypt(byte[] data) {
        this.sm3c3.update(data, data.length);

        for(int i = 0; i < data.length; ++i) {
            if (this.keyOff == this.key.length) {
                this.NextKey();
            }

            byte var10002 = data[i];
            byte[] var10003 = this.key;
            byte var10006 = this.keyOff;
            this.keyOff = (byte)(var10006 + 1);
            data[i] = (byte)(var10002 ^ var10003[var10006]);
        }

    }

    protected void Init_dec(BigInteger userD, ECPoint c1) {
        this.p2 = c1.multiply(userD);
        this.Reset();
    }

    protected void Decrypt(byte[] data) {
        for(int i = 0; i < data.length; ++i) {
            if (this.keyOff == this.key.length) {
                this.NextKey();
            }

            byte var10002 = data[i];
            byte[] var10003 = this.key;
            byte var10006 = this.keyOff;
            this.keyOff = (byte)(var10006 + 1);
            data[i] = (byte)(var10002 ^ var10003[var10006]);
        }

        this.sm3c3.update(data, data.length);
    }

    protected void Dofinal(byte[] c3) {
        byte[] p = Util.byteConvert32Bytes(this.p2.getY().toBigInteger());
        this.sm3c3.update(p, p.length);
        this.sm3c3.doFinal(c3);
        this.Reset();
    }
}

