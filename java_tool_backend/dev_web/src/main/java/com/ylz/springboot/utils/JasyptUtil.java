package com.ylz.springboot.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;

/**
 * jasypt 加密方式
 *
 * @author lhh
 * @date 2019/9/9
 */
public class JasyptUtil {




    /**jasypt 加密算法
     * Pwd.
     *
     * @param plaintext 要加密的文本信息 例："rszsk"
     */
    public static void enpwd(String plaintext){
        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //自己在用的时候更改此密码
        config.setPassword("bc63e6012bd38ba5");
        //应用配置
        encryptor.setConfig(config);
        //加密
        String ciphertext=encryptor.encrypt(plaintext);
        System.out.println("明文:"+plaintext + " ，密文: " + ciphertext);
    }


    /**jasypt 解密算法
     * De pwd.
     *
     * @param ciphertext 解密的文本信息  例：PovZgl9pg6IXlalIyavYG6HQBq4NyM96
     */
    public static void dePwd(String ciphertext){

        //加密工具
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        //加密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        //自己在用的时候更改此密码
        config.setPassword("bc63e6012bd38ba5");
        //应用配置
        encryptor.setConfig(config);
        //解密
        String plaintext=encryptor.decrypt(ciphertext);
        System.out.println("密文:"+ciphertext + " ，明文: " + plaintext);
    }
}
