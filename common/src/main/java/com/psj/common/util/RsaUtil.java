package com.psj.common.util;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Administrator
 */
@Slf4j
public class RsaUtil {

    private static final String RSA = "RSA";
    private static final String CIPHER_RSA = "RSA/ECB/PKCS1Padding";
    private static final String BAR = "-";
    private static final char SOFT_BLANK = '\r';

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 解密
     *
     * @param cipherText  密文
     * @param privatePath 私钥路径
     * @return 返回解密后的字符串
     */
    public static String decrypt(String cipherText, String privatePath) throws Exception {
        PrivateKey privateKey = RsaUtil.loadPrivateKey(getPrivateKeyString(privatePath));
        byte[] decryptByte = RsaUtil.decryptData(Base64Util.decode(cipherText), privateKey);
        if (decryptByte != null) {
            return new String(decryptByte, "utf-8");
        }
        return null;
    }


    /**
     * 解密
     *
     * @param cipherText 密文
     * @return 返回解密后的字符串
     */
    public static String decrypt(String cipherText) throws Exception {
        return decrypt(cipherText, null);
    }

    /**
     * 加密
     *
     * @param plainTest  明文
     * @param publicPath 公钥路径
     * @return 返回加密后的密文
     */
    public static String encrypt(String plainTest, String publicPath) throws Exception {
        PublicKey publicKey = RsaUtil.loadPublicKey(getPublicKeyString(publicPath));
        return Base64Util.encode(RsaUtil.encryptData(plainTest.getBytes(), publicKey));
    }

    /**
     * 加密
     *
     * @param plainTest 明文
     * @return 返回加密后的密文
     */
    public static String encrypt(String plainTest) throws Exception {
        return encrypt(plainTest);
    }

    /**
     * 获取公钥字符串
     */
    public static String getPublicKeyString(String publicPath) {
        publicPath = publicPath == null ? PathUtil.getRootPath() + "/public_key.pem" : publicPath;
        return getKeyString(publicPath);
    }

    /**
     * 获取私钥字符串
     */
    private static String getPrivateKeyString(String privatePath) {
        privatePath = privatePath == null ? PathUtil.getRootPath() + "/private_key.pem" : privatePath;
        return getKeyString(privatePath);
    }

    /**
     * 获取秘钥字符串
     *
     * @param path 密码文件路径
     */
    private static String getKeyString(String path) {
        try {
            @Cleanup FileInputStream fis = new FileInputStream(path);
            return readKey(fis);
        } catch (IOException e) {
            log.error("获取密码文件错误", e);
        }
        return null;
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data      需加密数据的byte数据
     * @param publicKey 公钥
     * @return 加密后的byte型数据
     */
    private static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_RSA);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            log.error("公钥加密错误", e);
            return null;
        }
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     */
    private static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            log.error("私钥解密错误", e);
            return null;
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    private static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Util.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     */
    private static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Util.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            return KeyFactory.getInstance(RSA).generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 读取密钥信息
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            System.out.println(readLine);
            if (!readLine.startsWith(BAR)) {
                sb.append(readLine);
                sb.append(SOFT_BLANK);
            }
        }
        return sb.toString();
    }

}
