package com.newer.guobin.service;

import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.vo.PageResult;

import java.util.List;

public interface TravelGroupService {
    /**
     * 新增自由行
     * @param travelGroup
     */
    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    /**
     * 分页查询自由行
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    TravelGroup findById(Integer id);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    /**
     * 使用跟团游id，查询跟团游和自由行中间表，获取自由行id的集合
     * @param id
     * @return
     */
    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    /**
     * 根据id删除旅游团
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有跟团游数据
     * @return
     */
    List<TravelGroup> findAll();
}
