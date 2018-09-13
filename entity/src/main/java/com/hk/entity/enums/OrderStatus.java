package com.hk.entity.enums;

/**
 * @author 何康
 * @date 2018/9/13 9:05
 */
public enum OrderStatus {
    INIT("初始化"),
    PROCESS("处理中"),
    SUCCESS("处理成功"),
    FAIL("处理失败")
    ;

    private String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
