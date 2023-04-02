package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

//    @Select("select * from user")
    List<User> list();
//  通过条件进行查询,这里写的是一个基类，传入一个子类，是一个向上转型的类型，使代码变得更通用
    List<User> listByCondition(BaseRequest baseRequest);
// 添加用户
    void save(User obj);
// 通过id查询
    User getById(Integer id);
// 更新数据
    void updateById(User obj);


    void deleteById(Integer id);

    User getByNo(String userNo);
}
