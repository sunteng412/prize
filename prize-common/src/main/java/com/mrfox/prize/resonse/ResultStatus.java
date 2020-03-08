package com.mrfox.prize.resonse;

/**
 * @author : MrFox
 * @create_date : 2019-07-07 16:25
 * @version: : v1.0
 * @update_date :
 * @update_by :
 * 响应实体
 **/
public enum ResultStatus {

    /**
     * 响应成功
     **/
    SUCCESS(200),

    /**
     * 接口请求失败
     **/
    FAIL(400),

    /**
     * 身份验证失败
     **/
    UNAUTHORIZED(401),

    /**
     * 系统错误
     **/
    SYSTEM_FAIL(500);

    private Integer status;

    ResultStatus(Integer code) {
        this.status = code;
    }

    public Integer getStatus() {
        return this.status;
    }

}