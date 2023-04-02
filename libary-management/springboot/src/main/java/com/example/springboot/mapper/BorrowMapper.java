package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowMapper {

    List<Borrow> list();
//  通过条件进行查询
    List<Borrow> listByCondition(BaseRequest baseRequest);
// 添加用户
    void save(Borrow obj);
// 通过id查询
    Borrow getById(Integer id);
// 更新数据
    void updateById(Borrow obj);

    void deleteById(Integer id);

//    还书的操作
    List<Retur> listReturByCondition(BaseRequest baseRequest);

    void saveRetur(Retur obj);

    void deleteReturById(Integer id);

    void updateStatus(String status, Integer id);
}
