package com.example.springboot.controller.request;

import lombok.Data;

//分页信息的公共部分
@Data
public class BaseRequest {
//    默认将数据赋予初值，避免如果前端没有传递分页页数时，默认的查询页数
    private Integer pageNum=1;
    private Integer pageSize=10;
}
