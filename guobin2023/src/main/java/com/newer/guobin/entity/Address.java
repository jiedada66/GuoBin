package com.newer.guobin.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Integer id;
    private String addressName;
    private String lng;
    private String lat;
    }
