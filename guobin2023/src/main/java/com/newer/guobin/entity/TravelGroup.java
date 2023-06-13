package com.newer.guobin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 报团进行分组
 */
@Data
public class TravelGroup implements Serializable {
    private Integer id;//主键
    private String code;//旅行团项目编号
    private String name;//旅行团名称
    private String helpCode;//旅行团项目缩写
    private String sex;//适用性别
    private String remark;//介绍
    private String attention;//注意事项
    private List<TravelItem> travelItems;//一个报团包含多个自由行

    }
