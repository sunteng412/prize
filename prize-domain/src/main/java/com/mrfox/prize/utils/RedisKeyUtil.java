package com.mrfox.prize.utils;

/*****
 * 生成redis的key
 * @author     : MrFox
 * @date       : 2020-01-26 20:37
 * @description:
 * @version    :
 ****/
public class RedisKeyUtil {

    public static final String ACTIVE_KEY = "ACTIVE_";

    private RedisKeyUtil() {
    }

    /*****
     * 生成redis的key
     * @param keyPrefix 前缀
     * @param uniqueMark 唯一标识
     * @return String
     * @description:
     ****/
    public static String getRedisKey(String keyPrefix, Object uniqueMark) {
        return keyPrefix + uniqueMark.toString();
    }
}
