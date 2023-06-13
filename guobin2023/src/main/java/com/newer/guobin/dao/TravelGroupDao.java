package com.newer.guobin.dao;

import com.github.pagehelper.Page;
import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.entity.TravelItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {

    void add(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map<String, Integer> map);


    /**
     * 分页查询自由行
     * @param queryString
     * @return
     */
    Page<TravelGroup> findPage(String queryString);


    TravelGroup findById(Integer id);

    /**
     * 使用跟团游id，查询跟团游和自由行中间表，获取自由行id的集合
     * @param id
     * @return
     */
    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer id);

    void deleteById(Integer id);

    void edit(TravelGroup travelGroup);

    List<TravelGroup> findAll();

    List<TravelGroup> findTravelGroupListById(Integer id);


}
