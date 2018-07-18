package com.itsdf07.java;

import java.util.Arrays;

/**
 * @Description: 进制转换
 * @Author itsdf07
 * @Time 2018/7/18/018 13:58
 */

public class HexOct {

    public static void main(String[] args) throws Exception {
        int i = 0x0100;
        System.out.println(i);
//        byte b1 = (byte) 45;
//        System.out.println("1.字节转10进制:" + byte2Int(b1));
//
//        int i = 89;
//        System.out.println("2.10进制转字节:" + int2Byte(i));
//
//        byte[] b2 = new byte[]{(byte) 0xFF, (byte) 0x5F, (byte) 0x6, (byte) 0x5A};
//        System.out.println("3.字节数组转16进制字符串:" + bytes2HexString(b2));
//
//        String s1 = new String("1DA47C");
//        System.out.println("4.16进制字符串转字节数组:" + Arrays.toString(hexString2Bytes(s1)));
//
//        System.out.println("5.字节数组转字符串:" + bytes2String(b2));
//
//        System.out.println("6.字符串转字节数组:" + Arrays.toString(string2Bytes(s1)));
//
//        System.out.println("7.16进制字符串转字符串:" + hex2String(s1));
//
//        String s2 = new String("Hello!");
//        System.out.println("8.字符串转16进制字符串:" + string2HexString(s2));
    }

    /**
     * 字节转10进制
     *
     * @param b
     * @return
     */
    public static int byte2Int(byte b) {
        int r = (int) b;
        return r;
    }

    /**
     * 10进制转字节
     */
    public static byte int2Byte(int i) {
        byte r = (byte) i;
        return r;
    }

    /**
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }

    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }

    }

    /**
     * 字节数组转字符串
     */
    public static String bytes2String(byte[] b) throws Exception {
        String r = new String(b, "UTF-8");
        return r;
    }

    /**
     * 字符串转字节数组
     */
    public static byte[] string2Bytes(String s) {
        byte[] r = s.getBytes();
        return r;
    }

    /**
     * 16进制字符串转字符串
     */
    public static String hex2String(String hex) throws Exception {
        String r = bytes2String(hexString2Bytes(hex));
        return r;
    }

    /**
     * 字符串转16进制字符串
     */
    public static String string2HexString(String s) throws Exception {
        String r = bytes2HexString(string2Bytes(s));
        return r;
    }
}
