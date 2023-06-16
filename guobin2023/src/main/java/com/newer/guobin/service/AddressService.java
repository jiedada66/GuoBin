package com.newer.guobin.service;

import com.newer.guobin.entity.Address;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;

import java.util.List;

public interface AddressService {

    List<Address> findAll();
    void addAddress(Address address);
    PageResult findPage(QueryPageBean queryPageBean);
    void deleteById(Integer id);


}
