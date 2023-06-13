package com.newer.guobin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 自由行(单项旅行)
 */
@Data
public class TravelItem implements Serializable {
    private Integer id;//主键
    private String code;//自由行项目编号
    private String name;//项目名称
    private String sex;//适用性别
    private String age;//适用年龄（范围），例如：20-50
    private Float price;//价格
    private String type;//参团类型
    private String remark;//项目说明
    private String attention;//注意事项

}
