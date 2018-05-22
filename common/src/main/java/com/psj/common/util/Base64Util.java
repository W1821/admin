package com.psj.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64字符串编码工具类
 *
 * @author Administrator
 */
public class Base64Util {

    /**
     * 加密
     *
     * @param data 待加密的字节数组
     * @return 加密后的字符串
     */
    public static String encode(byte[] data) {
        if (data == null) {
            return null;
        }
        Base64 base64 = new Base64();
        return new String(base64.encode(data));
    }

    /**
     * 加密
     *
     * @param data 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encode(String data) {
        if (data == null) {
            return null;
        }
        return encode(data.getBytes());
    }

    /**
     * 解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串，如果str是null或长度为0，返回str
     */
    public static byte[] decode(String str) {
        Base64 base64 = new Base64();
        return base64.decode(str);
    }


}

