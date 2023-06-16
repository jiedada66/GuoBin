package com.newer.guobin.dao;

import com.github.pagehelper.Page;
import com.newer.guobin.entity.Address;
import com.newer.guobin.entity.Member;

import java.util.List;

public interface AddressDao {

    List<Address> findAll();

    void addAddress(Address address);

    Page<Address> selectByCondition(String queryString);

    void deleteById(Integer id);


}
