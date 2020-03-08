package com.mrfox.prize.pattern.prize.strategy.select;

import java.lang.annotation.*;

/*****
 * 抽奖算法
 * @author     : MrFox
 * @date       : 2020-01-27 15:42
 * @description:
 * @version    :
 ****/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {
    /**
     * 缺省扩展点名。
     */
    String value() default "";
}
