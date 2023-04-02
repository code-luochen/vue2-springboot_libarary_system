package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

//    根究id查询用户数据
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
       Admin admin = adminService.getById(id);
       return Result.success(admin);
    }

//    添加新用户
    @PostMapping("/save")
    public Result save(@RequestBody Admin obj){
        adminService.save(obj);
        return Result.success();
    }

//    登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        LoginDTO login = adminService.login(request);
        return Result.success(login);
}

//修改密码
    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest request){
      adminService.changePass(request);
      return Result.success();
}
//    根据id删除数据
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return  Result.success();
    }
//     编辑修改用户
    @PutMapping("/update")
    public Result update(@RequestBody Admin obj){
        adminService.update(obj);
        return  Result.success();
    }

//    查询全部
    @GetMapping("/list")
    public Result list(){
        List<Admin> admins = adminService.list();
        return Result.success(admins);
    }
//    分页查询，可以根据条件查询
    @GetMapping("/page")
    public Result page(AdminPageRequest adminPageRequest){
        return Result.success(adminService.page(adminPageRequest));
    }

}
