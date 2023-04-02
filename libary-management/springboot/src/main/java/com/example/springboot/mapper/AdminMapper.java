package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Admin> list();
//  通过条件进行查询
    List<Admin> listByCondition(BaseRequest baseRequest);
// 添加用户
    void save(Admin obj);
// 通过id查询
    Admin getById(Integer id);
// 更新数据
    void updateById(Admin obj);

    void deleteById(Integer id);

    Admin getByUsernameandPassword(@Param("username") String username,@Param("password") String password);

    int updtatePassword(PasswordRequest request);

    Admin getByUsername(String username);
}
