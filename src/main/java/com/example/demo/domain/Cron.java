package com.example.demo.domain;


import lombok.Data;

@Data
public class Cron {

    private String id;

     //cron表达式
    private String express;

    //状态("1":有效   "0":无效)
    private String status;

}