package com.mrfox.prize.contants;

/*****
 * 中奖状态
 * @author     : MrFox
 * @date       : 2020-01-24 22:06
 * @description:
 * @version    :
 ****/
public enum PrizeLuckyStatus {
    /**
     * 1-中奖,2-未中奖,3-未知
     */
    WIN(1),
    FAIL(2),
    UNKNOWN(3);
    private int status;

    PrizeLuckyStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
