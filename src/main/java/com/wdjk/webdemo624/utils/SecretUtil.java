package com.wdjk.webdemo624.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-07 00:47
 **/
public class SecretUtil {
    /**
     *生成随机数
     *      - Math.random() 能生成 >= 0 且 < 1 的双精度伪随机数
     *      - 指定 min（最小值），max（最大值）min <= random numbers <= max
     *      - 参考公式：(随机数 * (max - min + 1)) + 1
     *      - int 极限值范围
     *          - 最大值：Integer.MAX_VALUE = 0x7fffffff = 2147483647
     *          - 最小值：Integer.MIN_VALUE = 0x80000000 =-2147483648
     * @param min
     * @param max
     * @return
     */
    public static int generateRandomNumbers(int min, int max){
        return (int) ((Math.random() * (max - min + 1))) + min;
    }

    /**
     * MD5加密
     *
     * @param plainText 明文
     * @return String MD5密文
     */
    public static String encryptMd5(String plainText){
        if (StringUtils.isBlank(plainText)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }


}
