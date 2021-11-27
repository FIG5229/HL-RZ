package com.integration.sgcc.sm;

/**
 * @author demo
 */
public class SM3Utils {
    private static final int BYTE_LENGTH = 32;
    private static final int BLOCK_LENGTH = 64;
    private static final int BUFFER_LENGTH = 64;
    private byte[] xBuf = new byte[64];
    private int xBufOff;
    private byte[] V;
    private int cntBlock;

    public SM3Utils() {
        this.V = (byte[])SM3.iv.clone();
        this.cntBlock = 0;
    }

    public SM3Utils(SM3Utils t) {
        this.V = (byte[])SM3.iv.clone();
        this.cntBlock = 0;
        System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
        this.xBufOff = t.xBufOff;
        System.arraycopy(t.V, 0, this.V, 0, t.V.length);
    }

    protected int doFinal(byte[] out) {
        if (out == null) {
            return 0;
        } else {
            byte[] tmp = this.SG_SM3Final();
            System.arraycopy(tmp, 0, out, 0, tmp.length);
            return 32;
        }
    }

    protected void reset() {
        this.xBufOff = 0;
        this.cntBlock = 0;
        this.V = (byte[])SM3.iv.clone();
    }

    protected void update(byte[] in, int len) {
        int partLen = 64 - this.xBufOff;
        int inputLen = in.length;
        int dPos = 0;
        if (in != null && inputLen > 0) {
            if (partLen < inputLen) {
                System.arraycopy(in, dPos, this.xBuf, this.xBufOff, partLen);
                inputLen -= partLen;
                dPos += partLen;
                this.doUpdate();

                while(inputLen > 64) {
                    System.arraycopy(in, dPos, this.xBuf, 0, 64);
                    inputLen -= 64;
                    dPos += 64;
                    this.doUpdate();
                }
            }

            System.arraycopy(in, dPos, this.xBuf, this.xBufOff, inputLen);
            this.xBufOff += inputLen;
        }
    }

    public void SG_SM3UpDate(byte[] in) {
        int partLen = 64 - this.xBufOff;
        int inputLen = in.length;
        int dPos = 0;
        if (in != null && inputLen > 0) {
            if (partLen < inputLen) {
                System.arraycopy(in, dPos, this.xBuf, this.xBufOff, partLen);
                inputLen -= partLen;
                dPos += partLen;
                this.doUpdate();

                while(inputLen > 64) {
                    System.arraycopy(in, dPos, this.xBuf, 0, 64);
                    inputLen -= 64;
                    dPos += 64;
                    this.doUpdate();
                }
            }

            System.arraycopy(in, dPos, this.xBuf, this.xBufOff, inputLen);
            this.xBufOff += inputLen;
        }
    }

    protected void doUpdate() {
        byte[] B = new byte[64];

        for(int i = 0; i < 64; i += 64) {
            System.arraycopy(this.xBuf, i, B, 0, B.length);
            this.doHash(B);
        }

        this.xBufOff = 0;
    }

    private void doHash(byte[] B) {
        byte[] tmp = SM3.CF(this.V, B);
        System.arraycopy(tmp, 0, this.V, 0, this.V.length);
        ++this.cntBlock;
    }

    public byte[] SG_SM3Final() {
        byte[] B = new byte[64];
        byte[] buffer = new byte[this.xBufOff];
        if (this.xBufOff == 0) {
            return null;
        } else {
            System.arraycopy(this.xBuf, 0, buffer, 0, buffer.length);
            byte[] tmp = SM3.padding(buffer, this.cntBlock);

            for(int i = 0; i < tmp.length; i += 64) {
                System.arraycopy(tmp, i, B, 0, B.length);
                this.doHash(B);
            }

            return this.V;
        }
    }

    protected void update(byte in) {
        byte[] buffer = new byte[]{in};
        this.update(buffer, 1);
    }

    protected int getDigestSize() {
        return 32;
    }
}
