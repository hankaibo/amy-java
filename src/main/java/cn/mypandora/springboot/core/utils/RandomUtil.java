package cn.mypandora.springboot.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * RandomUtil
 *
 * @author hankaibo
 * @date 2019/1/12
 */
public class RandomUtil {

    /**
     * 获取默认位数（12位）的盐
     *
     * @return 12位的随机盐
     */
    public static String getSalt() {
        return getSalt(12);
    }

    /**
     * 获取随机盐。
     * 使用SecureRandom安全的伪随机数。
     *
     * @param size
     * @return
     */
    public static String getSalt(int size) {
        return RandomStringUtils.randomAlphabetic(size);
    }

    /**
     * 获取UUID。
     *
     * @return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取不带横线的UUID。
     *
     * @return UUID
     */
    public static String getShortUUID() {
        return getUUID().replaceAll("-", "");
    }
}
