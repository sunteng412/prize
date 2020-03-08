package com.mrfox.prize.configuration.read;

import lombok.Data;

/*****
 * 封装配置实体类
 * @author     : MrFox
 * @date       : 2020-01-25 17:38
 * @description:
 * @version    :
 ****/
@Data
public class CacheInitConfigEntity {

    /**
     * 缓存key名
     * */
    private String keyName;

    /**
     * class名
     * */
    private String className;

    /**
     * 缓存时间
     * */
    private Integer expireDate;

    /**
     * 缓存时间单位
     * */
    private String expireDateUnit;
}
