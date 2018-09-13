package com.hk.entity.enums;

/**
 * @author 何康
 * @date 2018/9/13 9:04
 */
public enum OrderType {
    APPLY("申购"),
    REDEEM("兑现")
    ;

    private String desc;

    OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
