package com.newer.guobin.service;

import com.newer.guobin.vo.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map map) throws Exception;

    //根据id查询预约信息，包括人信息、套餐信息
    Map findByIdForDetail(Integer id) throws Exception;

}
