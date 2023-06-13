package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.entity.OrderSetting;
import com.newer.guobin.service.OrderSettingService;
import com.newer.guobin.util.POIUtils;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/orderSetting")
public class OrderSettingController {

    private final OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            // 使用poi工具类解析excel文件，读取里面的内容
            List<String[]> lists = POIUtils.readExcel(excelFile);
            // 把List<String[]> 数据转换成 List<OrderSetting>数据
            List<OrderSetting> orderSettings = new ArrayList<>();
            //  迭代里面的每一行数据，进行封装到集合里面
            for (String[] str : lists) {
                // 获取到一行里面，每个表格数据，进行封装
                OrderSetting orderSetting = new OrderSetting(new Date(str[0]),Integer.parseInt(str[1]));
                orderSettings.add(orderSetting);
            }
            // 调用业务进行保存
            orderSettingService.add(orderSettings);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }


    /**
     * 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {//参数格式为：2022-12
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            System.out.println(list);
            //获取预约设置数据成功
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            //获取预约设置数据失败
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据指定日期修改可预约人数
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            //预约设置成功
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //预约设置失败
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    }



