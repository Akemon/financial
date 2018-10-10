package com.hk.manager.error;

/**
 * @author 何康
 * @date 2018/9/14 18:59
 */



/***
 * 错误种类
 */
public enum ErrorEnum {
    ID_NOT_NULL("F001","订单编号为空",false),
    REWARD_ERROR("F002","收益率范围错误",false),
    STEPAMOUNT_ERROR("F003","投资步长需为整数",false),
    UNKNOWN("999","未知异常",false)
    ;
    private String code;
    private String message;
    private boolean canRetry;

    ErrorEnum(String code, String message, boolean canRetry) {
        this.code = code;
        this.message = message;
        this.canRetry = canRetry;
    }

    /***
     * 通过code获取异常的详细信息
     * @param code
     * @return
     */
    public static ErrorEnum getByCode(String code){
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if(errorEnum.code.equals(code)){
                return errorEnum;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCanRetry() {
        return canRetry;
    }
}
