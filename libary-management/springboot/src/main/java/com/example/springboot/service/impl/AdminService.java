package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
    //    默认的密码
    private static final String DEFAULT_PASS = "123456";
    //    数据的加盐字符串
    private static final String PASS_SALT = "luochun";
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    //    模糊查询（这里做的是条件查询），通过输入的数据进行查询
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
//        可以根据传进来的这个对象，获取到对象中的数据
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    //    添加新用户
    @Override
    public void save(Admin obj) {
//        先判断用户是否传入了密码，如果传入了就用用户的，如果没有就默认设置一个
        if (StrUtil.isBlank(obj.getPassword())) {
            //        添加一个默认的密码
            obj.setPassword(DEFAULT_PASS);
        }
//    将数据进行加盐处理
        obj.setPassword(securePass(obj.getPassword()));//md5加密，加盐
        try {
            adminMapper.save(obj);
        }catch (DuplicateKeyException e){
            log.error("数据插入失败，username{}",obj.getUsername());
            throw  new ServiceException("用户名重复");
        }

    }

    //       根据用户的id查询
    @Override
    public Admin getById(Integer id) {
        Admin user = adminMapper.getById(id);
        return user;
    }

    @Override
    public void update(Admin user) {
//        手动设置更新时间
        user.setUpdatetime(new Date());
        adminMapper.updateById(user);
    }

    //   根据id删除数据
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {

        Admin admin = null;

//        由于查询的时候可能会出现同一个用户名有多个的情况，所以需要对代码进行保护,这是人为的想法，可能会出现查到多条的状态
        try {
            //  查询出一个唯一的用户
            admin = adminMapper.getByUsername(request.getUsername());
        } catch (Exception e) {
            log.error("根据用户名查询出错{}", request.getUsername());
            throw new ServiceException("用户名错误");
        }
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }

        //  密码的加密进行处理，和数据库中的代码相对应
        request.setPassword(securePass(request.getPassword()));
//            当这个用户是存在的时候，我们再去判断密码是否合法，由于已经将admin这个用户查询出来的，他的密码是有的
        if (!request.getPassword().equals(admin.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }

//            如果是Boolean则可以进行getStatus ,但是用的是boolean所以用的is
        if (!admin.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);

//            生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());

        loginDTO.setToken(token);
        return loginDTO;

    }

    @Override
    public void changePass(PasswordRequest request) {
//        注意 我们需要对新的密码进行加密
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updtatePassword(request);
        if (count <= 0) {
            throw new ServiceException("修改密码失败");
        }

    }

    //    密码加密处理
    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }
}
