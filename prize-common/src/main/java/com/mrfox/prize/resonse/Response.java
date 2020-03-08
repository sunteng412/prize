package com.mrfox.prize.resonse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author z0228
 */
@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class Response<T> implements Serializable {
    public static final String GENERAL_SUCCESS_CODE = "maskit.success.general";
    public static final String GENERAL_SUCCESS_MESSAGE = "Maskit general success";
    public static final String GENERAL_FAILURE_CODE = "maskit.failure.general";
    public static final String GENERAL_FAILURE_MESSAGE = "Maskit general failure";
    public static final int GENERAL_SUCCESS_STATUS = 200;
    public static final int GENERAL_FAILURE_STATUS = 400;

    @Getter
    @Setter
    private int status = GENERAL_SUCCESS_STATUS;
    @Getter
    @Setter
    private String error = GENERAL_SUCCESS_CODE;
    @JsonIgnore
    private Object[] args = null;
    @Getter
    @Setter
    private String message = GENERAL_SUCCESS_MESSAGE;
    @Getter
    @Setter
    @JsonSerialize(nullsUsing = ResponseNullTimestampSerializer.class, converter = ResponseTimestampStringConverter.class)
    @JsonDeserialize(converter = ResponseStringTimestampConverter.class)
    private Date timestamp = null;
    @Getter
    @Setter
    private T data = null;
    @Getter
    @Setter
    private String path = null;

    public Response(int status, String error, String message, T data) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean success() {
        return status == 200 || status > 200 && status < 300;
    }

    public static <T> Response<T> yes() {
        return new Response(GENERAL_SUCCESS_STATUS, GENERAL_SUCCESS_CODE, GENERAL_SUCCESS_MESSAGE, true);
    }

    public static <T> Response<T> yes(final T data) {
        return new Response(GENERAL_SUCCESS_STATUS, GENERAL_SUCCESS_CODE, GENERAL_SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> yes(final T data, final String code) {
        return new Response(GENERAL_SUCCESS_STATUS, code, GENERAL_SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> yes(final T data, final int status) {
        return new Response(status, GENERAL_SUCCESS_CODE, GENERAL_SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> yes(final T data, final int status, final String code) {
        return new Response(status, code, GENERAL_SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> yes(final T data, final int status, final String code, final String message) {
        return new Response(status, code, message, data);
    }

    public static <T> Response<T> no() {
        return new Response(GENERAL_FAILURE_STATUS, GENERAL_FAILURE_CODE, GENERAL_FAILURE_MESSAGE, null);
    }

    public static <T> Response<T> no(final String code) {
        return new Response(GENERAL_FAILURE_STATUS, code, GENERAL_FAILURE_MESSAGE, null);
    }

    public static <T> Response<T> no(final int status) {
        return new Response(status, GENERAL_FAILURE_CODE, GENERAL_FAILURE_MESSAGE, null);
    }

    public static <T> Response<T> no(final int status, final String code) {
        return new Response(status, code, GENERAL_FAILURE_MESSAGE, null);
    }

    public static <T> Response<T> no(final int status, final String code, final String message) {
        return new Response(status, code, message, null);
    }

    public static <T> Response<T> no(final int status, final String code, final T data) {
        return new Response(status, code, GENERAL_FAILURE_MESSAGE, data);
    }

    public static <T> Response<T> no(final T data) {
        return new Response<>(GENERAL_FAILURE_STATUS, GENERAL_FAILURE_CODE, GENERAL_FAILURE_MESSAGE, data);
    }

    public static <T> Response<T> auto(final Boolean success, final String code, final T data) {
        return new Response(success ? GENERAL_SUCCESS_STATUS : GENERAL_FAILURE_STATUS,
            code, success ? GENERAL_SUCCESS_MESSAGE : GENERAL_FAILURE_MESSAGE, data);
    }

    public static <I, T> Response<T> auto(final I input, final ResponseAdapter<I> adapter) {
        return adapter.apply(input);
    }

    public void args(Object... args) {
        this.args = args;
    }

    public Object[] args() {
        return this.args;
    }
}
