package com.newer.guobin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newer.guobin.constant.RedisConstant;
import com.newer.guobin.dao.SetmealDao;
import com.newer.guobin.entity.Setmeal;
import com.newer.guobin.service.SetmealService;
import com.newer.guobin.vo.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealDao setmealDao;
    private final JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupId) {
        // 1：新增套餐
        setmealDao.add(setmeal);
        // 2：向套餐和跟团游的中间表中插入数据
        if(travelgroupId != null && travelgroupId.length > 0){
            //绑定套餐和跟团游的多对多关系
            setSetmealAndTravelGroup(setmeal.getId(),travelgroupId);
        }
        //将图片名称保存到Redis
        savePicToRedis(setmeal.getImg());

    }

    //将图片名称保存到Redis
    private void savePicToRedis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    //绑定套餐和跟团游的多对多关系
    private void setSetmealAndTravelGroup(Integer id, Integer[] travelgroupId) {
        for (Integer checkgroupId : travelgroupId) {
            Map<String, Integer> map = new HashMap<>();
            map.put("travelgroup_id",checkgroupId);
            map.put("setmeal_id",id);
            setmealDao.setSetmealAndTravelGroup(map);
        }
    }

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        List<Integer> ids = setmealDao.findTravelGroupIdBySetmealId(id);
        if (!ids.isEmpty()) {
            throw new RuntimeException("不允许删除！");
        }
        setmealDao.deleteTravelGroupIdAndSetmealId(id);
        setmealDao.deleteById(id);
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findTravelGroupIdBySetmealId(Integer id) {
        return setmealDao.findTravelGroupIdBySetmealId(id);
    }

    @Override
    public void edit(Integer[] travelItemIds, Setmeal setmeal) {
        // 1：修改套餐的基本信息
        setmealDao.edit(setmeal);
        /**
         * 2：修改跟团游和套餐的中间表（先删除，再创建）
         * 之前的数据删除
         * 再新增页面选中的数据
         */
        // 删除之前中间表的数据
        setmealDao.deleteTravelGroupIdAndSetmealId(setmeal.getId());
        // 再新增页面选中的数据
        setSetmealAndTravelGroup(setmeal.getId(),travelItemIds);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    public Setmeal findDescById(int id) {
        return setmealDao.findDescById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }


}
