package com.newer.guobin.dao;

import com.github.pagehelper.Page;
import com.newer.guobin.entity.TravelItem;

import java.util.List;

public interface TravelItemDao {

    /**查询所有
     * @return
     */
    List<TravelItem> findAll();

    /**
     * 新增自由行
     * @param item
     */
    void add(TravelItem item);

    /**
     * 分页查询自由行
     * @param queryString
     * @return
     */
    Page<TravelItem> findPage(String queryString);

    /**
     * 根据id删除自由行
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询是否存在此id的自由行
     * @param id
     * @return
     */
    long findCountByTravelItemItemId(Integer id);

    /**
     *根据id查询
     * @param id
     * @return
     */
    TravelItem findById(Integer id);

    /**
     * 编辑自由行
     * @param travelItem
     */
    void edit(TravelItem travelItem);

    List<TravelItem> findTravelItemListById(Integer id);

}
