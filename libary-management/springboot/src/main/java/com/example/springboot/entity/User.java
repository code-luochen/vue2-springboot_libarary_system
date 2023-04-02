package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User extends BaseEntity {
    private String name;
    private String username;
    private Integer age;
    private String sex;
    private String address;
    private String phone;
    private Integer account;
    private boolean status;

//    用来存放账户金额的值
    private Integer score;

}
