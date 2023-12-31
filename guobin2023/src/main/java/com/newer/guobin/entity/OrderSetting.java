package com.newer.guobin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 参团订单预约设置
 */
@Data
public class OrderSetting implements Serializable{
    private Integer id ;
    private Date orderDate;//预约设置日期
    private int number;//可预约人数
    private int reservations ;//已预约人数

    public OrderSetting() {
    }

    public OrderSetting(Date orderDate, int number) {
        this.orderDate = orderDate;
        this.number = number;
    }

    }
