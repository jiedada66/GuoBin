package com.newer.guobin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 旅行套餐
 */
@Data
public class Setmeal implements Serializable {
    private Integer id;
    private String name;
    private String code;
    private String helpCode;
    private String sex;//套餐适用性别：0不限 1男 2女
    private String age;//套餐适用年龄
    private Float price;//套餐价格
    private String remark;
    private String attention;
    private String img;//套餐对应图片存储路径
    private List<TravelGroup> travelGroups;//旅行套餐对应的报团分组，多对多关系

    }
