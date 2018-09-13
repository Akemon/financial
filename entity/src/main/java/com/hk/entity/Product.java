package com.hk.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 何康
 * @date 2018/9/13 8:37
 */
@Entity
@Data
public class Product {

    @Id
    private String id;

    //产品名称
    private String name;

    //起投金额
    private BigDecimal thresholdMount;

    //投资步长
    private BigDecimal stepAmount;

    //锁定期
    private Integer lockTerm;

    //收益率
    private BigDecimal rewardRate;

    //产品状态
    private String status;

    //产品备注
    private String memo;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", thresholdMount=" + thresholdMount +
                ", stepAmount=" + stepAmount +
                ", lockTerm=" + lockTerm +
                ", rewardRate=" + rewardRate +
                ", status='" + status + '\'' +
                ", memo='" + memo + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
