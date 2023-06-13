package com.newer.guobin.service.impl;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.dao.OrderSettingDao;
import com.newer.guobin.entity.OrderSetting;
import com.newer.guobin.service.OrderSettingService;
import com.newer.guobin.util.DateUtils;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderSettingServiceImpl implements OrderSettingService {

    private final OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        // 1：遍历List<OrderSetting>
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 如果设置过预约日期，更新number数量
            if (count>0){
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else {
                // 如果没有设置过预约日期，执行保存
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**  传递的参数
     *   date（格式：2022-12）
     *  构建的数据List<Map>
     *    map.put("date",1);
     map.put("number",120);
     map.put("reservations",10);

     *  查询方案：SELECT * FROM t_ordersetting WHERE orderDate LIKE '2022-12-%'
     *  查询方案：SELECT * FROM t_ordersetting WHERE orderDate BETWEEN '2022-12-01' AND '2022-12-31'
     */
//根据日期查询预约设置数据
    public List<Map> getOrderSettingByMonth(String date) {//2022-12
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = date + "-01"; //2022-12-01
        String dateEnd = DateUtils.getLastDayOfMonth(date); //2022-12-31
        Map<String,Object>  map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        // 2.查询当前月份的预约设置
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        System.out.println(list);
        List<Map> data = new ArrayList<>();
        // 3.将List<OrderSetting>，组织成List<Map>
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }



}
