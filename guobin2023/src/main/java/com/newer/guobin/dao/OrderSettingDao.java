package com.newer.guobin.dao;

import com.newer.guobin.entity.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    //根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date date);

    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);

}
