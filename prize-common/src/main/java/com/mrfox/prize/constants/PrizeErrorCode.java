package com.mrfox.prize.constants;

public enum PrizeErrorCode {
    POINT_IS_NOT_ENOUGH("point.is.not.enough", "积分数不够"),
    ACTIVE_IS_EXPIRE("active.is.expire", "活动不在有效区间内"),
    DRAW_COUNT_IS_OVER_THE_LIMIT("draw.count.is.over.the.limit", "抽奖次数超过限制")
    ;

    private final String code;
    private final String errMsg;

    PrizeErrorCode(String code, String errMsg) {
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
