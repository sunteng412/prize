package com.mrfox.prize.resonse;

public enum ErrorCode {

    QUERY_OBJECT_NULL_FAIL("query.object.null.fail", "查询不到对应信息"),
    STATUS_ILLEGAL("status.illegal", "状态非法"),
    SYSTEM_ERROR("system.error", "系统错误"),
    OBJECT_EXIST("object.exist", "对象已存在"),
    INSERT_FAIL("insert.fail", "新增失败"),
    DELETE_FAIL("delete.fail", "删除失败"),
    UPDATE_FAIL("update.fail", "更新失败"),
    QUERY_FAIL("query.fail", "查询失败"),
   ;

    private final String code;
    private final String errMsg;

    ErrorCode(String code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public String code() {
        return this.code;
    }
    public String errMsg() {
        return this.errMsg;
    }
    
}
