package com.ylz.springboot.utils;

import java.util.UUID;

/**
 * KeyGenerateUtil
 *
 * @author: Chris
 * @time: 2019.02.14
 */
public class KeyGenerateUtil {

    /**
     * 生成Oracle主键UUID
     *
     * @return
     */
    public static String generateOracleId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
