package com.mrfox.prize.exception;

/**
 * roses异常规范
 *
 * @author fengshuonan
 * @date 2017-12-17-下午10:22
 */
public interface AbstractBaseExceptionEnum {

    /**
     * 获取异常的状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取异常的提示信息
     *
     * @return 提示
     */
    String getMessage();
}
