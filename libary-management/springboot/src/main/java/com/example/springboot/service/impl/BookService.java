package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class BookService implements IBookService {


    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    //    模糊查询（这里做的是条件查询），通过输入的数据进行查询
    @Override
    public PageInfo<Book> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
//        自关联查询
        List<Book> list = bookMapper.listByCondition(baseRequest);
        return new PageInfo<>(list);
    }

    @Override
    public void save(Book obj) {
        try {
            obj.setCategory(category(obj.getCategories()));
            bookMapper.save(obj);
        }catch (Exception e){
            throw new ServiceException("数据插入错误",e);
        }

    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void update(Book obj) {
        try{
            obj.setCategory(category(obj.getCategories()));
            obj.setUpdatetime(LocalDate.now());
            bookMapper.updateById(obj);
        }catch (Exception e){
            throw new ServiceException("数据更新错误",e);
        }

    }

//用来处理categories和category的公用函数
    private String category (List<String > categories){
        StringBuilder sb = new StringBuilder();
        if(CollUtil.isNotEmpty(categories)){
            categories.forEach(v->sb.append(v).append(" > "));
            return sb.toString().substring(0,sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
}
