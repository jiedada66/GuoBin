package com.newer.guobin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newer.guobin.dao.TravelGroupDao;
import com.newer.guobin.dao.TravelItemDao;
import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.entity.TravelItem;
import com.newer.guobin.service.TravelGroupService;
import com.newer.guobin.service.TravelItemService;
import com.newer.guobin.vo.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    private final TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.add(travelGroup);
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        // 新增几条数据，由travelItemIds的长度决定
        if (travelItemIds!=null && travelItemIds.length>0){
            for (Integer travelItemId : travelItemIds) {
                // 如果有多个参数使用map
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroup",travelGroupId);
                map.put("travelItem",travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<TravelGroup> page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelGroupId(id);
    }

    @Override
    public void deleteById(Integer id) {
        TravelGroup travelGroup =  travelGroupDao.findById(id);
        if (travelGroup != null){
            travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(id);
            travelGroupDao.deleteById(id);
        }
        else {
            throw new RuntimeException("数据已经不存在");
        }
    }


    @Override
    public void edit(Integer[] travelitemIds, TravelGroup travelGroup) {
        // 1：修改跟团游的基本信息
        travelGroupDao.edit(travelGroup);
        /**
         * 2：修改跟团游和自由行的中间表（先删除，再创建）
         * 之前的数据删除
         * 再新增页面选中的数据
         */
        // 删除之前中间表的数据
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        // 再新增页面选中的数据
        setTravelGroupAndTravelItem(travelGroup.getId(),travelitemIds);
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupDao.findAll();
    }


}
