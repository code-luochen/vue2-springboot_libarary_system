package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    //    根究id查询用户数据
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        User user = iUserService.getById(id);
        return Result.success(user);
    }

    //    添加新用户
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        iUserService.save(user);
        return Result.success();
    }

    //    充值金额
//    添加新用户
    @PostMapping("/account")
    public Result account(@RequestBody User user) {
        iUserService.handleAccount(user);
        return Result.success();
    }

    //    根据id删除数据
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        iUserService.deleteById(id);
        return Result.success();
    }

    //     编辑修改用户
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        iUserService.update(user);
        return Result.success();
    }

    //    查询全部
    @GetMapping("/list")
    public Result list() {
        List<User> users = iUserService.list();
        return Result.success(users);
    }

    //    分页查询，可以根据条件查询
    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(iUserService.page(userPageRequest));
    }

}
