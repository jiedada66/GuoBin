package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.constant.RedisConstant;
import com.newer.guobin.entity.Setmeal;
import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.service.SetmealService;
import com.newer.guobin.util.QiniuUtils;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    private final SetmealService setmealService;
    private final JedisPool jedisPool;

    // 图片上传,imgFile:需要跟页面el-upload里面的name保持一致
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 找到.最后出现的位置
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] travelgroupIds){
        try {
            setmealService.add(setmeal,travelgroupIds);
        }catch (Exception e){
            //新增套餐失败
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        //新增套餐成功
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        log.info("分页查询成功。。。");
        PageResult pageResult = setmealService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setmealService.deleteById(id);
            return new Result(true,"删除成功");
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    //根据id查询
    @GetMapping("/findById")
    public Result findById(Integer id) {
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealService.findById(id));
    }

    //通过套餐id查找跟团游id
    @GetMapping("/findTravelGroupIdBySetmealId")
    public Result findTravelGroupIdBySetmealId(Integer id) {
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealService.findTravelGroupIdBySetmealId(id));
    }

    //编辑
    @PostMapping("/edit")
    public Result edit(Integer[] travelgroupIds,@RequestBody Setmeal setmeal ){
        setmealService.edit(travelgroupIds,setmeal);
        return new Result(true,"修改成功！");
    }

    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try{
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    //根据id查询套餐信息
    @RequestMapping("/findDescById")
    public Result findDescById(int id){
        try{
            Setmeal setmeal = setmealService.findDescById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


}
