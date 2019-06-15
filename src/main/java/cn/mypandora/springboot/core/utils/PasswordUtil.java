package cn.mypandora.springboot.core.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * PasswordUtil
 *
 * @author hankaibo
 * @date 2019/1/12
 */
public class PasswordUtil {

    /**
     * 加密密码。
     *
     * @param password  明文密码
     * @param salt      盐
     * @return          官方密码
     */
    public static String encrypt(String password,String salt){
        return new Md5Hash(password,salt,4).toBase64();
    }
}
