package com.example.springboot.service;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest baseRequest);

    void save(Borrow obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void deleteById(Integer id);

//    还书的操作

    PageInfo<Retur> pageRetur(BaseRequest baseRequest);

    void saveRetur(Retur obj);

    void deleteReturById(Integer id);


}
