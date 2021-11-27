package com.integration.sgcc.sm;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author demo
 */
public class SM2Utils {
    public SM2Utils() {
    }

    public byte[] SG_generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters)key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters)key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();
        byte[] priTmp = privateKey.toByteArray();
        byte[] pri = new byte[32];
        ECPoint userKey = sm2.ecc_point_g.multiply(privateKey);
        if (userKey.getX() == null) {
            return null;
        } else {
            if (priTmp.length == 33) {
                System.arraycopy(priTmp, 1, pri, 0, 32);
            }

            if (priTmp.length == 32) {
                System.arraycopy(priTmp, 0, pri, 0, 32);
            }

            if (priTmp.length == 31) {
                System.arraycopy(priTmp, 0, pri, 1, 31);
            }

            return priTmp.length <= 33 && priTmp.length >= 31 ? Util.hexToByte(Util.byteToHex(pri) + Util.byteToHex(publicKey.getEncoded())) : null;
        }
    }

    protected byte[] SG_SM2Sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException {
        if (privateKey != null && privateKey.length == 32) {
            if (sourceData != null && sourceData.length != 0) {
                byte[] userIdTmp = new byte[]{49, 50, 51, 52, 53, 54, 55, 56, 49, 50, 51, 52, 53, 54, 55, 56};
                if (userId == null) {
                    userId = new byte[16];
                    System.arraycopy(userIdTmp, 0, userId, 0, 16);
                }

                if (userId.length < 1) {
                    return null;
                } else {
                    SM2 sm2 = SM2.Instance();
                    BigInteger userD = new BigInteger(1, privateKey);
                    ECPoint userKey = sm2.ecc_point_g.multiply(userD);
                    if (userKey.getX() == null) {
                        return null;
                    } else {
                        SM3Utils sm3 = new SM3Utils();
                        byte[] z = sm2.sm2GetZ(userId, userKey);
                        sm3.update(z, z.length);
                        sm3.update(sourceData, sourceData.length);
                        byte[] md = new byte[32];
                        sm3.doFinal(md);
                        SM2Result sm2Result = new SM2Result();
                        sm2.sm2Sign(md, userD, userKey, sm2Result);
                        String sr = sm2Result.r.toString(16);
                        String ss = sm2Result.s.toString(16);
                        if (sr.length() != 64) {
                            while(sr.length() != 64) {
                                sr = '0' + sr;
                            }
                        }

                        if (ss.length() != 64) {
                            while(ss.length() != 64) {
                                ss = '0' + ss;
                            }
                        }

                        return Util.hexToByte(sr + ss);
                    }
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    protected boolean SG_SM2VerifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData) throws IOException {
        if (publicKey != null && publicKey.length == 65) {
            if (sourceData != null && sourceData.length >= 1) {
                if (signData != null && signData.length == 64) {
                    byte[] userIdTmp = new byte[]{49, 50, 51, 52, 53, 54, 55, 56, 49, 50, 51, 52, 53, 54, 55, 56};
                    if (userId == null) {
                        userId = new byte[16];
                        System.arraycopy(userIdTmp, 0, userId, 0, 16);
                    }

                    if (userId.length < 1) {
                        return false;
                    } else {
                        SM2 sm2 = SM2.Instance();
                        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
                        SM3Utils sm3 = new SM3Utils();
                        byte[] z = sm2.sm2GetZ(userId, userKey);
                        sm3.update(z, z.length);
                        sm3.update(sourceData, sourceData.length);
                        byte[] md = new byte[32];
                        sm3.doFinal(md);
                        byte[] r = new byte[32];
                        System.arraycopy(signData, 0, r, 0, 32);
                        byte[] s = new byte[32];
                        System.arraycopy(signData, 32, s, 0, 32);
                        byte[] tr = new byte[32];
                        byte[] ts = new byte[32];
                        if (!Arrays.equals(r, tr) && !Arrays.equals(s, ts)) {
                            BigInteger br = new BigInteger(1, r);
                            BigInteger bs = new BigInteger(1, s);
                            SM2Result sm2Result = new SM2Result();
                            sm2Result.r = br;
                            sm2Result.s = bs;
                            sm2.sm2Verify(md, userKey, sm2Result.r, sm2Result.s, sm2Result);
                            return sm2Result.r.equals(sm2Result.R);
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public byte[] SG_SM2EncData(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey != null && publicKey.length == 65) {
            if (data != null && data.length != 0) {
                byte[] source = new byte[data.length];
                System.arraycopy(data, 0, source, 0, data.length);
                Cipher cipher = new Cipher();
                SM2 sm2 = SM2.Instance();
                ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
                ECPoint c1 = cipher.Init_enc(sm2, userKey);
                cipher.Encrypt(source);
                byte[] c3 = new byte[32];
                cipher.Dofinal(c3);
                return Util.hexToByte(Util.byteToHex(c1.getEncoded()) + Util.byteToHex(c3) + Util.byteToHex(source));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public byte[] SG_SM2DecData(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey != null && privateKey.length == 32) {
            if (encryptedData != null && encryptedData.length >= 97) {
                String data = Util.byteToHex(encryptedData);
                byte[] c1Bytes = Util.hexToByte(data.substring(0, 130));
                int c2Len = encryptedData.length - 97;
                byte[] c2 = Util.hexToByte(data.substring(194, 194 + 2 * c2Len));
                byte[] c3 = Util.hexToByte(data.substring(130, 194));
                byte[] tc3 = Util.hexToByte(data.substring(130, 194));
                SM2 sm2 = SM2.Instance();
                BigInteger userD = new BigInteger(1, privateKey);
                Cipher cipher = new Cipher();

                try {
                    ECPoint c1 = sm2.ecc_curve.decodePoint(c1Bytes);
                    cipher.Init_dec(userD, c1);
                    cipher.Decrypt(c2);
                    cipher.Dofinal(c3);
                } catch (Exception var13) {
                    var13.printStackTrace();
                    return null;
                }

                return (new String(c3)).equals(new String(tc3)) ? c2 : null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

