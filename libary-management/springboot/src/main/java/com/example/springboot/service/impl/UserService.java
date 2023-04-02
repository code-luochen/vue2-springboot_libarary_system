package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

//    模糊查询（这里做的是条件查询），通过输入的数据进行查询
    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(),baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

//    添加新用户
    @Override
    public void save(User user) {

        Date date = new Date();
//        自己添加一个用户的数据来充当卡号,hashCode()可以返回一个数字类型的数据，和前面的拼接后就成为一个纯数字的数据Math.abs()将数据取绝对值
        user.setUsername(DateUtil.format(date,"yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.save(user);
    }
//       根据用户的id查询
    @Override
    public User getById(Integer id) {
       User user = userMapper.getById(id);
        return user;
    }

    @Override
    public void update(User user) {
//        手动设置更新时间
        user.setUpdatetime(LocalDate.now());
        userMapper.updateById(user);
    }

//   根据id删除数据
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

//   账户充值
    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if(score==null){
            return;
        }
//        从数据库中查询数据出来
        User dbUser = userMapper.getById(user.getId());
        dbUser.setAccount(dbUser.getAccount()+score);
        if(dbUser.getAccount()>0){
            dbUser.setStatus(true);
        }
        userMapper.updateById(dbUser);
    }
}
