package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.constant.RedisMessageConstant;
import com.newer.guobin.entity.Order;
import com.newer.guobin.service.OrderService;
import com.newer.guobin.util.SMSUtils;
import com.newer.guobin.util.ValidateCodeUtils;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private final JedisPool jedisPool;

    //预约时发送手机验证码
    @RequestMapping("/sendForOrder")
    public Result send4Order(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(4);//生成4位数字验证码
        try {
            //发送短信
            SMSUtils.sendShortMessage(telephone,code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, code.toString());
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}






