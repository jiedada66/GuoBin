package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.constant.RedisMessageConstant;
import com.newer.guobin.entity.Order;
import com.newer.guobin.service.OrderService;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    private final JedisPool jedisPool;

    /**
     * 旅游预约
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        // 1、获取手机号
        String telephone = (String) map.get("telephone");
        // 2、获取验证码
        String validateCode = (String) map.get("validateCode");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        //校验手机验证码
        if(codeInRedis == null || !codeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        //调用旅游预约服务
        try{
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            // 断点调试，查看map里面封装了哪些数据
            result = orderService.order(map);
        }catch (Exception e){
            e.printStackTrace();
            //预约失败
            return result;
        }
//        if(result.isFlag()){
//            //预约成功，发送短信通知，短信通知内容可以是“预约时间”，“预约人”，“预约地点”，“预约事项”等信息。
//            String orderDate = (String) map.get("orderDate");
//            try {
//                SMSUtils.sendShortMessage(telephone,orderDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Map map = null;
        try{
            map = orderService.findByIdForDetail(id);
            //查询预约信息成功
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
