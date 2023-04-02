package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookRequest;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.example.springboot.service.IBookService;
import com.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    IBorrowService borrowService;

//    根究id查询用户数据
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
       Borrow borrow = borrowService.getById(id);
       return Result.success(borrow);
    }

//    添加新用户
    @PostMapping("/save")
    public Result save(@RequestBody Borrow obj){
        borrowService.save(obj);
        return Result.success();
    }

//    根据id删除数据
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        borrowService.deleteById(id);
        return  Result.success();
    }
//     编辑修改用户
    @PutMapping("/update")
    public Result update(@RequestBody Borrow obj){
        borrowService.update(obj);
        return  Result.success();
    }

//    查询全部
    @GetMapping("/list")
    public Result list(){
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }
//    分页查询，可以根据条件查询
    @GetMapping("/page")
    public Result page(BorrowPageRequest borrowPageRequest){
        return Result.success(borrowService.page(borrowPageRequest));
    }

//    还书操作

    //    分页查询，可以根据条件查询
    @GetMapping("/pageRetur")
    public Result pageRetur(BorrowPageRequest borrowPageRequest){
        return Result.success(borrowService.pageRetur(borrowPageRequest));
    }

    //    添加新用户
    @PostMapping("/saveRetur")
    public Result saveRetur(@RequestBody Retur obj){
        borrowService.saveRetur(obj);
        return Result.success();
    }

    //    根据id删除数据
    @DeleteMapping("/deleteRetur/{id}")
    public Result deleteRetur(@PathVariable Integer id){
        borrowService.deleteReturById(id);
        return  Result.success();
    }
}
