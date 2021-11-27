package com.integration.sgcc.sm;

import java.math.BigInteger;

/**
 * @author demo
 */
public class Util {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public Util() {
    }

    protected static byte[] intToBytes(int num) {
        byte[] bytes = new byte[]{(byte)(255 & num >> 0), (byte)(255 & num >> 8), (byte)(255 & num >> 16), (byte)(255 & num >> 24)};
        return bytes;
    }

    protected static int byteToInt(byte[] bytes) {
        int num = 0;
        int temp = (255 & bytes[0]) << 0;
        num = num | temp;
        temp = (255 & bytes[1]) << 8;
        num |= temp;
        temp = (255 & bytes[2]) << 16;
        num |= temp;
        temp = (255 & bytes[3]) << 24;
        num |= temp;
        return num;
    }

    protected static byte[] longToBytes(long num) {
        byte[] bytes = new byte[8];

        for(int i = 0; i < 8; ++i) {
            bytes[i] = (byte)((int)(255L & num >> i * 8));
        }

        return bytes;
    }

    protected static byte[] byteConvert32Bytes(BigInteger n) {
        byte[] tmpd = (byte[])null;
        if (n == null) {
            return null;
        } else {
            if (n.toByteArray().length == 33) {
                tmpd = new byte[32];
                System.arraycopy(n.toByteArray(), 1, tmpd, 0, 32);
            } else if (n.toByteArray().length == 32) {
                tmpd = n.toByteArray();
            } else {
                tmpd = new byte[32];

                for(int i = 0; i < 32 - n.toByteArray().length; ++i) {
                    tmpd[i] = 0;
                }

                System.arraycopy(n.toByteArray(), 0, tmpd, 32 - n.toByteArray().length, n.toByteArray().length);
            }

            return tmpd;
        }
    }

    protected static BigInteger byteConvertInteger(byte[] b) {
        if (b[0] < 0) {
            byte[] temp = new byte[b.length + 1];
            temp[0] = 0;
            System.arraycopy(b, 0, temp, 1, b.length);
            return new BigInteger(temp);
        } else {
            return new BigInteger(b);
        }
    }

    protected static String getHexString(byte[] bytes) {
        return getHexString(bytes, true);
    }

    protected static String getHexString(byte[] bytes, boolean upperCase) {
        String ret = "";

        for(int i = 0; i < bytes.length; ++i) {
            ret = ret + Integer.toString((bytes[i] & 255) + 256, 16).substring(1);
        }

        return upperCase ? ret.toUpperCase() : ret;
    }

    protected static void printHexString(byte[] bytes) {
        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            System.out.print("0x" + hex.toUpperCase() + ",");
        }

        System.out.println("");
    }

    protected static byte[] hexStringToBytes(String hexString) {
        if (hexString != null && !hexString.equals("")) {
            hexString = hexString.toUpperCase();
            int length = hexString.length() / 2;
            char[] hexChars = hexString.toCharArray();
            byte[] d = new byte[length];

            for(int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    protected static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    protected static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    protected static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var5 = 0; i < l; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
        }

        return out;
    }

    protected static String encodeHexString(byte[] data) {
        return encodeHexString(data, true);
    }

    protected static String encodeHexString(byte[] data, boolean toLowerCase) {
        return encodeHexString(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexString(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    protected static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toDigit(data[j], j) << 4;
                ++j;
                f |= toDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    protected static String StringToAsciiString(String content) {
        String result = "";
        int max = content.length();

        for(int i = 0; i < max; ++i) {
            char c = content.charAt(i);
            String b = Integer.toHexString(c);
            result = result + b;
        }

        return result;
    }

    protected static String hexStringToString(String hexString, int encodeType) {
        String result = "";
        int max = hexString.length() / encodeType;

        for(int i = 0; i < max; ++i) {
            char c = (char)hexStringToAlgorism(hexString.substring(i * encodeType, (i + 1) * encodeType));
            result = result + c;
        }

        return result;
    }

    protected static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;

        for(int i = max; i > 0; --i) {
            char c = hex.charAt(i - 1);
            int algorism;
            if (c >= '0' && c <= '9') {
                algorism = c - 48;
            } else {
                algorism = c - 55;
            }

            result = (int)((double)result + Math.pow(16.0D, (double)(max - i)) * (double)algorism);
        }

        return result;
    }

    protected static String hexStringToBinary(String hex) {
        hex = hex.toUpperCase();
        String result = "";
        int max = hex.length();

        for(int i = 0; i < max; ++i) {
            char c = hex.charAt(i);
            switch(c) {
                case '0':
                    result = result + "0000";
                    break;
                case '1':
                    result = result + "0001";
                    break;
                case '2':
                    result = result + "0010";
                    break;
                case '3':
                    result = result + "0011";
                    break;
                case '4':
                    result = result + "0100";
                    break;
                case '5':
                    result = result + "0101";
                    break;
                case '6':
                    result = result + "0110";
                    break;
                case '7':
                    result = result + "0111";
                    break;
                case '8':
                    result = result + "1000";
                    break;
                case '9':
                    result = result + "1001";
                case ':':
                case ';':
                case '<':
                case '=':
                case '>':
                case '?':
                case '@':
                default:
                    break;
                case 'A':
                    result = result + "1010";
                    break;
                case 'B':
                    result = result + "1011";
                    break;
                case 'C':
                    result = result + "1100";
                    break;
                case 'D':
                    result = result + "1101";
                    break;
                case 'E':
                    result = result + "1110";
                    break;
                case 'F':
                    result = result + "1111";
            }
        }

        return result;
    }

    protected static String AsciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;

        for(int i = 0; i < length; ++i) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char)a;
            String d = String.valueOf(b);
            result = result + d;
        }

        return result;
    }

    protected static String algorismToHexString(int algorism, int maxLength) {
        String result = "";
        result = Integer.toHexString(algorism);
        if (result.length() % 2 == 1) {
            result = "0" + result;
        }

        return patchHexString(result.toUpperCase(), maxLength);
    }

    protected static String byteToString(byte[] bytearray) {
        String result = "";
        int length = bytearray.length;

        for(int i = 0; i < length; ++i) {
            char temp = (char)bytearray[i];
            result = result + temp;
        }

        return result;
    }

    protected static int binaryToAlgorism(String binary) {
        int max = binary.length();
        int result = 0;

        for(int i = max; i > 0; --i) {
            char c = binary.charAt(i - 1);
            int algorism = c - 48;
            result = (int)((double)result + Math.pow(2.0D, (double)(max - i)) * (double)algorism);
        }

        return result;
    }

    protected static String algorismToHEXString(int algorism) {
        String result = "";
        result = Integer.toHexString(algorism);
        if (result.length() % 2 == 1) {
            result = "0" + result;
        }

        result = result.toUpperCase();
        return result;
    }

    protected static String patchHexString(String str, int maxLength) {
        String temp = "";

        for(int i = 0; i < maxLength - str.length(); ++i) {
            temp = "0" + temp;
        }

        str = (temp + str).substring(0, maxLength);
        return str;
    }

    protected static int parseToInt(String s, int defaultInt, int radix) {
        boolean var3 = false;

        int i;
        try {
            i = Integer.parseInt(s, radix);
        } catch (NumberFormatException var5) {
            i = defaultInt;
        }

        return i;
    }

    protected static int parseToInt(String s, int defaultInt) {
        boolean var2 = false;

        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException var4) {
            i = defaultInt;
        }

        return i;
    }

    protected static byte[] hexToByte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            char[] arr = hex.toCharArray();
            byte[] b = new byte[hex.length() / 2];
            int i = 0;
            int j = 0;

            for(int l = hex.length(); i < l; ++j) {
                String swap = "" + arr[i++] + arr[i];
                int byteint = Integer.parseInt(swap, 16) & 255;
                b[j] = (new Integer(byteint)).byteValue();
                ++i;
            }

            return b;
        }
    }

    protected static String byteToHex(byte[] b) {
        if (b == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        } else {
            String hs = "";
            String stmp = "";

            for(int n = 0; n < b.length; ++n) {
                stmp = Integer.toHexString(b[n] & 255);
                if (stmp.length() == 1) {
                    hs = hs + "0" + stmp;
                } else {
                    hs = hs + stmp;
                }
            }

            return hs.toUpperCase();
        }
    }

    protected static byte[] subByte(byte[] input, int startIndex, int length) {
        byte[] bt = new byte[length];

        for(int i = 0; i < length; ++i) {
            bt[i] = input[i + startIndex];
        }

        return bt;
    }
}