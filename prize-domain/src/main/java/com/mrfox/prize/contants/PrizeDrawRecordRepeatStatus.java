package com.mrfox.prize.contants;

/*****
 * 中奖状态
 * @author     : MrFox
 * @date       : 2020-01-24 22:06
 * @description:
 * @version    :
 ****/
public enum PrizeDrawRecordRepeatStatus {
    /**
     * 1-处理成功,2-处理失败,3-未知
     */
    HANDLE(1),
    HANDLE_ERROR(2),
    UNKNOWN(3);
    private int status;

    PrizeDrawRecordRepeatStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
