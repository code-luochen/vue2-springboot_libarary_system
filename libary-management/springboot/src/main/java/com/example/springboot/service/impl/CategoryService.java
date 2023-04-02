package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CategoryService implements ICategoryService {


    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    //    模糊查询（这里做的是条件查询），通过输入的数据进行查询
    @Override
    public PageInfo<Category> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
//        自关联查询
        List<Category> categories = categoryMapper.listByCondition(baseRequest);
        return new PageInfo<>(categories);
    }

    @Override
    public void save(Category obj) {
        categoryMapper.save(obj);
    }

    @Override
    public Category getById(Integer id) {
        return categoryMapper.getById(id);
    }

    @Override
    public void update(Category obj) {

        obj.setUpdatetime(LocalDate.now());
            categoryMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
