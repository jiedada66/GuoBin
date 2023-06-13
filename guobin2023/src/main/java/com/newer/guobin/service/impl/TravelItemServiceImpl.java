package com.newer.guobin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newer.guobin.dao.TravelItemDao;
import com.newer.guobin.entity.TravelItem;
import com.newer.guobin.service.TravelItemService;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TravelItemServiceImpl implements TravelItemService{

    private final TravelItemDao travelItemDao;

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }

    /**
     * 新增自由行
     * @param travelItem
     */
    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        // 在删除自由行之前，先判断自由行的id，在中间表中是否存在数据
        long count =  travelItemDao.findCountByTravelItemItemId(id);
        // 中间表如果有数据，不要往后面执行，直接抛出异常
        // 如果非要删除也可以：delete from t_travelgroup_travelitem where travelitem_id = 1
        if (count > 0){
            throw new RuntimeException("不允许删除");
        }
        travelItemDao.deleteById(id);
    }

    @Override
    public TravelItem findById(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }
}
