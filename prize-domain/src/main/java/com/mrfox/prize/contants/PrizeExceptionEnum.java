package com.mrfox.prize.contants;


import com.mrfox.prize.exception.AbstractBaseExceptionEnum;

/**
 * @Author: core模块的异常集合
 * @Date: Created in 13:45 2019/7/5
 * @Description:
 * @Modified: By：
 */
public enum PrizeExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 服务器异常
     */
    SERVER_ERROR(500, "服务器异常"),

    /**
     * 数据库中有重复的参数
     */
    THERE_ARE_DUPLICATE_PARAMETERS_IN_THE_DATABASE(501, "数据库重复"),

    /**
     * 参数校验错误
     */
    PARAMETER_VERIFICATION_FAILED(400, "参数校验错误"),


    /**
     * 更新失败
     */
    UPDATE_FAILED(502, "更新失败"),

    /**
     * 查询失败
     */
    SELECT_FAILED(503, "查询失败"),

    /**
     * 保存失败
     */
    INSERT_FAILED(504, "保存失败"),

    /**
     * 删除失败
     */
    DELECT_FAILED(505, "删除失败");

    private Integer code;

    private String message;


    PrizeExceptionEnum(
            int code, String
            message) {
        this.code = code;
        this.message = message;
    }



    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
