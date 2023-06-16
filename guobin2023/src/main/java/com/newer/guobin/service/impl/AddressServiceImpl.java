package com.newer.guobin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newer.guobin.dao.AddressDao;
import com.newer.guobin.entity.Address;
import com.newer.guobin.service.AddressService;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Address> page = addressDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById(id);
    }


}
