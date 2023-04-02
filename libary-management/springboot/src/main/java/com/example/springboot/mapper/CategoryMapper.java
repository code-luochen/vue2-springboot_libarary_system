package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> list();
//  通过条件进行查询
    List<Category> listByCondition(BaseRequest baseRequest);
// 添加用户
    void save(Category obj);
// 通过id查询
    Category getById(Integer id);
// 更新数据
    void updateById(Category obj);

    void deleteById(Integer id);


}
