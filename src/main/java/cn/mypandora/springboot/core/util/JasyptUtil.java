package cn.mypandora.springboot.core.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * JasyptUtil 加密解密工具类，方便对密码等进行加密。
 *
 * @author hankaibo
 * @date 2019/9/15
 * @see <a href="https://blog.csdn.net/Rambo_Yang/article/details/107579388">参考文章</a>
 */
public class JasyptUtil {

    private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";
    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";

    /**
     * Jasyp加密（PBEWithMD5AndDES）
     * 
     * @param plainText
     *            待加密的原文
     * @param key
     *            加密秘钥
     * @return java.lang.String
     */
    public static String encryptWithMD5(String plainText, String key) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(key);
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * Jaspy解密（PBEWithMD5AndDES）
     * 
     * @param encryptedText
     *            待解密密文
     * @param key
     *            解密秘钥
     * @return java.lang.String
     */
    public static String decryptWithMD5(String encryptedText, String key) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(key);
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    /**
     * 
     * Jasyp 加密（PBEWITHHMACSHA512ANDAES_256）
     * 
     * @param plainText
     *            待加密的原文
     * @param key
     *            加密秘钥
     * @return java.lang.String
     */
    public static String encryptWithSHA512(String plainText, String key) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * Jaspy解密（PBEWITHHMACSHA512ANDAES_256）
     * 
     * @param encryptedText
     *            待解密密文
     * @param key
     *            解密秘钥
     * @return java.lang.String
     */
    public static String decryptWithSHA512(String encryptedText, String key) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    public static void main(String[] args) {
        // 获取vm中自定义密钥
        String key = System.getProperty("jasypt.encryptor.password", "123456");
        // 明文
        String plainText = "123456";

        // MD5 密文及明文
        String encryptWithMD5Str = encryptWithMD5(plainText, key);
        String decryptWithMD5Str = decryptWithMD5(encryptWithMD5Str, key);

        // SHA512 密文及明文
        String encryptWithSHA512Str = encryptWithSHA512(plainText, key);
        String decryptWithSHA512Str = decryptWithSHA512(encryptWithSHA512Str, key);

        System.out.println("采用MD5加密前原文密文：" + encryptWithMD5Str);
        System.out.println("采用MD5解密后密文原文: " + decryptWithMD5Str);
        System.out.println();
        System.out.println("采用SHA512加密前原文密文：" + encryptWithSHA512Str);
        System.out.println("采用SHA512解密后密文原文: " + decryptWithSHA512Str);

    }

}
