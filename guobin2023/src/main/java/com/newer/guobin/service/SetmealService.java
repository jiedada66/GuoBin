package com.newer.guobin.service;

import com.newer.guobin.entity.Setmeal;
import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface SetmealService {

    /**
     * 新增
     * @param setmeal
     * @param travelgroupIds
     */
    void add(Setmeal setmeal, Integer[] travelgroupIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 过套餐id查找跟团游id
     * @param id
     * @return
     */
    List<Integer> findTravelGroupIdBySetmealId(Integer id);

    /**
     * 编辑
     * @param travelItemIds
     * @param setmeal
     */
    void edit(Integer[] travelItemIds, Setmeal setmeal);

    /**
     * 查询所有
     * @return
     */
    List<Setmeal> findAll();

    Setmeal findDescById(int id);

    List<Map<String, Object>> findSetmealCount();

}
