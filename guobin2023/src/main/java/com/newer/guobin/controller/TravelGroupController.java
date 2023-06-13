package com.newer.guobin.controller;

import com.newer.guobin.constant.MessageConstant;
import com.newer.guobin.entity.TravelGroup;
import com.newer.guobin.entity.TravelItem;
import com.newer.guobin.service.TravelGroupService;
import com.newer.guobin.service.TravelItemService;
import com.newer.guobin.vo.PageResult;
import com.newer.guobin.vo.QueryPageBean;
import com.newer.guobin.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/travelGroup")
public class TravelGroupController {

    private final TravelGroupService travelGroupService;

    @PostMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelGroupService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;

    }


    @PostMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupService.add(travelGroup,travelItemIds);
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);

    }

    // 使用跟团游id，查询跟团游和自由行中间表，获取自由行id的集合
    @RequestMapping("/findTravelItemIdByTravelGroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id){
        List<Integer> list =  travelGroupService.findTravelItemIdByTravelGroupId(id);
        return list;
    }

    // 使用id查询跟团游，进行表单回显
    @RequestMapping("/findById")
    public Result findById(Integer id){
        TravelGroup travelGroup =  travelGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    // 编辑跟团游（返回 public Result(boolean flag, String message)）
    @RequestMapping("/edit")
    public Result edit(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup ){
        travelGroupService.edit(travelItemIds,travelGroup);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    //删除旅游团
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            travelGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        }catch (RuntimeException e){
            // 运行时异常，表示自由行和跟团游的关联表中存在数据
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        // 查询所有的跟团游
        List<TravelGroup> travelGroupList = travelGroupService.findAll();
        if(travelGroupList != null && travelGroupList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,travelGroupList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }



}
