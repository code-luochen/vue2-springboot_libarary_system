package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

//    根究id查询用户数据
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
       Category category = categoryService.getById(id);
       return Result.success(category);
    }

//    添加新用户
    @PostMapping("/save")
    public Result save(@RequestBody Category obj){
        categoryService.save(obj);
        return Result.success();
    }

//    根据id删除数据
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return  Result.success();
    }
//     编辑修改用户
    @PutMapping("/update")
    public Result update(@RequestBody Category obj){
        categoryService.update(obj);
        return  Result.success();
    }

//    查询全部
    @GetMapping("/list")
    public Result list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }
    //    查询全部,用于添加图书时的查询全部分类数据
    @GetMapping("/tree")
    public Result tree(){
        List<Category> list = categoryService.list();

//          对list进行操作
//        List<Category> treeList = list.stream().filter(v -> v.getPid() == null).collect(Collectors.toList());//第一层数据
        return Result.success(createTree(null,list));//null 表示从第一级开始递归
    }
    /**
     * 递归生成菜单树,完全递归的方法来实现递归树
     */
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if(pid==null){
                if(category.getPid()==null){    //那这就是第一级节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }else{
                if (pid.equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
//            当children为[]的时候，将他设置成null
            if(CollUtil.isEmpty(category.getChildren())){
                category.setChildren(null);
            }
        }
        return treeList;
    }

//    分页查询，可以根据条件查询
    @GetMapping("/page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

}
