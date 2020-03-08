package com.mrfox.prize.pattern.prize.strategy.valid;

import java.lang.annotation.*;

/*****
 * 声明策略
 * @author     : MrFox
 * @date       : 2020-01-27 15:42
 * @description:
 * @version    :
 ****/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategy {

    /**
     * 模式名称-根据该模式设置策略
     * */
    String modelName() default "";

    /**
     * 排序-默认排最后
     * */
    int sort() default 10;
}
