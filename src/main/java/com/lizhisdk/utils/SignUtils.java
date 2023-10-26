package com.lizhisdk.utils;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

/**
 * 签名工具
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public class SignUtils {
    /**
     * 公钥
     */
    private static String PUBLIC_KEY;
    /**
     * 生成签名，采用非堆成加密
     *
     * @param secretKey
     * @return
     */
    public static String genSignByAsyEncryption(String secretKey) {
        RSA rsa = new RSA();
        // 获得私钥、私钥
        rsa.getPrivateKeyBase64();
        PUBLIC_KEY = rsa.getPublicKeyBase64();
        // 私钥加密，公钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(secretKey, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        // 将 byte 数组转为 Base64 字符串
        return Base64.getEncoder().encodeToString(encrypt);
    }

    /**
     * 获取解密的公钥
     *
     * @return 返回公钥进行解密
     */
    public static String getPublicKey() {
        return PUBLIC_KEY;
    }
    /**
     * 生成签名
     *
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
