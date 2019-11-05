package com.itheima.service;

import com.itheima.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    void rejister(User user) throws Exception;//注册用户


    int active(String code);//激活 激活码

    User login(String username, String password);//登陆

    void isActive();//判断是否被激活


}
