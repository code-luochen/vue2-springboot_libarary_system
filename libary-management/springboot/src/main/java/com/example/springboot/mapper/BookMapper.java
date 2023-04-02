package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> list();
//  通过条件进行查询
    List<Book> listByCondition(BaseRequest baseRequest);
// 添加用户
    void save(Book obj);
// 通过id查询
    Book getById(Integer id);
// 更新数据
    void updateById(Book obj);

    void deleteById(Integer id);


    Book getByNo(String bookNo);

    void updateNumByNo(String bookNo);
}
