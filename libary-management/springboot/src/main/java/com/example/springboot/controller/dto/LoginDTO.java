package com.example.springboot.controller.dto;

import lombok.Data;

/*
* 设置的一个登录dto可以返回我们规定的数据段，
* 前端不能返回一个带密码的数据，所以我们创建一个返回的查询对象
* */
@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String token;
}
