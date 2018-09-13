package com.hk.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 何康
 * @date 2018/9/13 8:36
 */
@Entity(name = "order_t")
@Data
public class Order {

    //订单编号
    @Id
    private String orderId;

    //渠道编号
    private String chanId;

    //产品编号
    private String productId;

    //渠道用户编号
    private String chanUserId;

    //订单类型
    /**
     * @See com.hk.entity.enums.OrderType
     */
    private String orderType;

    //订单状态
    private String orderStatus;

    //外部订单编号
    private String outerOrderId;

    //金额
    private BigDecimal amount;

    //备注
    private String memo;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
