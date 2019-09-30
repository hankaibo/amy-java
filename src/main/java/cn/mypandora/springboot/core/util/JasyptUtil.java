package cn.mypandora.springboot.core.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * JasyptUtil 加密解密工具类，方便对密码等进行加密。
 *
 * @author hankaibo
 * @date 2019/9/15
 * @note System.getenv()为获取当前系统的变量，使用时请先在系统变量中设置。考虑到缓存问题，有可能要重启才能正常使用。
 */
public class JasyptUtil {

    private final static String SALT = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");

    public static void encrypt() {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // 加密所需的salt(盐)
        basicTextEncryptor.setPassword(SALT);
        // 要加密的数据（数据库的用户名或密码）
        String username = basicTextEncryptor.encrypt("test");
        String password = basicTextEncryptor.encrypt("123456");
        String port = basicTextEncryptor.encrypt("6379");
        String redisPassword = basicTextEncryptor.encrypt("123456");
        String druidUsername = basicTextEncryptor.encrypt("admin");
        String druidPassword = basicTextEncryptor.encrypt("admin");
        // 拷贝到 application.yml 配置文件中即可。默认方式： ENC(生成的密码)
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("port:" + port);
        System.out.println("redisPassword:" + redisPassword);
        System.out.println("druidUsername:" + druidUsername);
        System.out.println("druidPassword:" + druidPassword);
    }

    public static void decrypt() {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        // 加密所需的salt(盐)
        basicTextEncryptor.setPassword(SALT);
        // 要解密的数据（数据库的用户名或密码）
        String username = basicTextEncryptor.decrypt("4ey06krrjh0AC/mm8862Mg==");
        String password = basicTextEncryptor.decrypt("DK7xwNsYX4KUWopl7JWkfA==");
        String port = basicTextEncryptor.decrypt("fDw5+LopsF4KnqpkpBW8Eg==");
        String redisPassword = basicTextEncryptor.decrypt("wf1D/Cl3hXMjQ0RXjKkI2Q==");
        String druidUsername = basicTextEncryptor.decrypt("IJYQuF6SgWsiLnapRKvcqA==");
        String druidPassword = basicTextEncryptor.decrypt("j7iWznUj3hK9EYK4TDyVSQ==");
        // 密钥请不要直接配置在 application.yml 中，可以配置在环境变量、系统变量、命令行参数等安全地方。
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        System.out.println("port:" + port);
        System.out.println("redisPassword:" + redisPassword);
        System.out.println("druidUsername:" + druidUsername);
        System.out.println("druidPassword:" + druidPassword);
    }

    public static void main(String[] args) {
        encrypt();
        decrypt();
    }
}
