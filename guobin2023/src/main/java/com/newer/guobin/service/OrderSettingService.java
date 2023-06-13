package com.newer.guobin.service;

import com.newer.guobin.entity.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void add(List<OrderSetting> orderSettings);

    List<Map> getOrderSettingByMonth(String date); //参数格式为：2022-12

    void editNumberByDate(OrderSetting orderSetting);
}
