package com.newer.guobin.dao;

import com.github.pagehelper.Page;
import com.newer.guobin.entity.Setmeal;
import com.newer.guobin.entity.TravelGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    /**
     * 添加
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加绑定套餐和跟团游的多对多关系
     * @param map
     */
    void setSetmealAndTravelGroup(Map<String, Integer> map);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 通过套餐id查找跟团游id
     * @param id
     * @return
     */
    List<Integer> findTravelGroupIdBySetmealId(Integer id);

    /**
     * 删除关联表中的数据
     * @param id
     */
    void deleteTravelGroupIdAndSetmealId(Integer id);

    /**
     * 删除套餐中的数据
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
     * 编辑
     * @param setmeal
     */
    void edit(Setmeal setmeal);

    /**
     * 查询所有
     * @return
     */
    List<Setmeal> findAll();

    Setmeal findDescById(int id);

    List<Map<String, Object>> findSetmealCount();

}
