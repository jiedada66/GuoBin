package com.newer.guobin.dao;

import com.newer.guobin.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findByIdForDetail(Integer id);

    int getTodayOrderNumber(String date);

    int getTodayVisitsNumber(String date);

    int getThisWeekAndMonthOrderNumber(Map<String, Object> map);

    int getThisWeekAndMonthVisitsNumber(Map<String, Object> map);

    List<Map<String,Object>> findHotSetmeal();


}
