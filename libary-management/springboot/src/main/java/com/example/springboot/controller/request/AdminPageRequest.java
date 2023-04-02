package com.example.springboot.controller.request;

import lombok.Data;

//用来做user的分页类
@Data
public class AdminPageRequest extends BaseRequest {
//    继承后可以写一些自己特有的数据
    private String username;
    private String phone;
    private String email;
}
