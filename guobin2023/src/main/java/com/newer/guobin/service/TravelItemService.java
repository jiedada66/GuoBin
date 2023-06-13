package com.newer.guobin.service;

import com.newer.guobin.entity.TravelItem;
import com.newer.guobin.vo.PageResult;

import java.util.List;

public interface TravelItemService {
    /**
     * 查询所有
     * @return
     */
     List<TravelItem> findAll();

    /**
     * 新增自由行
     * @param travelItem
     */
    void add(TravelItem travelItem);

    /**
     * 分页查询自由行
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id删除自由行
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询自由行
     * @param id
     * @return
     */
    TravelItem findById(Integer id);

    /**
     * 编辑自由行
     * @param travelItem
     */
    void edit(TravelItem travelItem);

}
