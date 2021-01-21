package com.miyou.miyouapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

/**
 * MD5加密工具类
 *
 */
public class MDUtils {

    /**
     * 加密
     *
     * @param origin
     *            要被加密的字符串
     * @param charsetname
     *            加密字符,如UTF-8
     */
    public static String MD5EncodeForHex(String origin, String charsetname)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return MD5EncodeForHex(origin.getBytes(charsetname));
    }

    public static String MD5EncodeForHex(byte[] origin) throws NoSuchAlgorithmException {
        return Hex.encodeHexString(digest("MD5", origin));
    }

    /**
     * 指定加密算法
     *
     * @throws NoSuchAlgorithmException
     */
    private static byte[] digest(String algorithm, byte[] source) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance(algorithm);
        return md.digest(source);
    }
}