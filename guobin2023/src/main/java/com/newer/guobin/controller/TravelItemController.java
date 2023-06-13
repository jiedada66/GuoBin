package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.entity.TravelItem;
import com.newer.guobin.service.TravelItemService;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/travelItem")
public class TravelItemController {

    private final TravelItemService travelItemService;

    @GetMapping("/findAll")
    public Result findAll() {
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS,travelItemService.findAll());
    }

    /**
     * 新增
     * @param item
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody TravelItem item) {
        try {
            travelItemService.add(item);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELITEM_FAIL);
        }

        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @PostMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelItemService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            travelItemService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        }catch (RuntimeException e){
            // 运行时异常，表示自由行和跟团游的关联表中存在数据
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelItem travelItem =  travelItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem){
        travelItemService.edit(travelItem);
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }



}
