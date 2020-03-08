package com.mrfox.prize.exception;

/**
 * 业务异常的封装
 *
 * @author wy
 * @date 2016年11月12日 下午5:05:10
 */
public class ServiceException extends RuntimeException {

    private final Integer code;

    private final String errorMessage;

    public ServiceException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ServiceException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
