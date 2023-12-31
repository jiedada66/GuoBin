package com.newer.guobin.job;

import com.newer.guobin.constant.RedisConstant;
import com.newer.guobin.util.QiniuUtils;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
public class ClearImgJob {

    private final JedisPool jedisPool;

    //清理图片
    public void clearImg() {
        //计算redis中两个集合的差值，获取垃圾图片名称
        // 需要注意：在比较的时候，数据多的放到前面，如果pic多，那么pic放到前面，db多，db放到前面
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String pic = iterator.next();
            System.out.println("删除图片的名称是：" + pic);
            //删除图片服务器中的图片文件
            QiniuUtils.deleteFileFromQiniu(pic);
            //删除redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }

    }
    }
