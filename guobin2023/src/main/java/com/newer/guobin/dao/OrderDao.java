package com.newer.guobin.dao;

import com.newer.guobin.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findByIdForDetail(Integer id);

}
