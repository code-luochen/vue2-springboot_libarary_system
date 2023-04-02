package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.Retur;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IBookService;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BorrowService implements IBorrowService {


    @Autowired
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }

    //    模糊查询（这里做的是条件查询），通过输入的数据进行查询
    @Override
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
//        自关联查询
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            LocalDate returnDate = borrow.getReturnDate();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(returnDate)) { //当前日期比归还的日期小一天
                borrow.setNote("即将到期");
            } else if (now.isEqual(returnDate)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(returnDate)) {
                borrow.setNote("已过期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    @Transactional
    public void save(Borrow obj) {
//        1、检验用户的积分是否足够
        String userNo = obj.getUserNo();
        User user = userMapper.getByNo(userNo);

        if (Objects.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
//        2、检验图书的数量是否足够
        String bookNo = obj.getBookNo();
        Book book = bookMapper.getByNo(bookNo);
        if (Objects.isNull(book)) {
            throw new ServiceException("所借图书不存在");
        }

        //        3、检验图书数量是否充足
        if (book.getNums() < 1) {
            throw new ServiceException("图书数量不足");
        }

        Integer account = user.getAccount();
        Integer score = book.getScore() * obj.getDays(); //借一本书的积分*借书的天数
//        4、检验账户余额
        if (account < score) {
            throw new ServiceException("当前用户积分不足");
        }


//        5、更新用户余额
        user.setAccount(account - score);
        userMapper.updateById(user);
//       6、更新图书的数量
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);

//        添加归还的日期
        obj.setReturnDate(LocalDate.now().plus(obj.getDays(), ChronoUnit.DAYS)); //当前的日期加上 days 得到归还的日期

        obj.setScore(score);
//        7、新增借书记录
        borrowMapper.save(obj);
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {
        obj.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(obj);
    }


    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    //    还书的操作
    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
//        自关联查询
        List<Retur> returs = borrowMapper.listReturByCondition(baseRequest);
        return new PageInfo<>(returs);
    }

    @Transactional
    @Override
    public void saveRetur(Retur obj) {
//        改变状态
        obj.setStatus("已归还");
        borrowMapper.updateStatus("已归还", obj.getId()); //用来还书的操作，将来借书中的状态改变成已归还的状态

        obj.setRealDate(LocalDate.now());

//        增加图书数量
//        obj.setId(null); //如果设置，新表中的id成功新开始，如果没有设置，就和借书的id一样
        bookMapper.updateNumByNo(obj.getBookNo());
        borrowMapper.saveRetur(obj);


        Book book = bookMapper.getByNo(obj.getBookNo());
//        返还用户的积分,当用户提前还书时应该返还积分
        if (book != null) {
            long until = 0;
            if (obj.getRealDate().isBefore(obj.getReturnDate())) {
//            他们两个相差多少天
                until = obj.getRealDate().until(obj.getReturnDate(), ChronoUnit.DAYS);
            }else if (obj.getRealDate().isAfter(obj.getReturnDate())) {  //逾期归还，扣除相应积分
                until = -obj.getReturnDate().until(obj.getRealDate(), ChronoUnit.DAYS);
            }
            int score = (int) until * book.getScore();//待归还的分数
            User user = userMapper.getByNo(obj.getUserNo());
            int account= user.getAccount()+ score;
            user.setAccount(account);
            if(account <0){
//                锁定状态
                user.setStatus(false);
            }
            userMapper.updateById(user);
        }
    }

    @Override
    public void deleteReturById(Integer id) {
        borrowMapper.deleteReturById(id);
    }



}
