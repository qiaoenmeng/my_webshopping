package com.itheima.service.impl;
import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtil;

public class UserServiceimpl implements UserService {
    /*
    * 注册用户
    * */
    @Override
    public void rejister(User user) throws Exception {
      //1.判断  判断用户名是否为空
        String username = user.getUsername();
         if (username==null  || "".equals(username.trim())){
             throw new RuntimeException(Constant.TRAVEL_USER_NAME_IS_NULL);

         }
         // 是否在数据库中存在

        UserDao userDao=new UserDaoImpl();
       // boolean flag = userDao.exists(username);
        if (userDao.exists(username)){//存在
            throw  new RuntimeException(Constant.TRAVEL_USER_NAME_EXISTS);
        }
        //不存在  1.存入数据库
        userDao.save(user);
            //2.发送邮件

                ////保存成功以后 可以立即发送邮件  激活的功能 一定是将status 将0 修改成 1  找到数据 才能修改
        MailUtil.sendMail(user.getEmail(),"<a href='http://localhost:8080/travel/UserServlet?method=active&code="+user.getCode()+"'>点我激活用户</a>");




    }
/*
* 激活激活码*/
    @Override
    public int active(String code) {

        UserDao userDao =new UserDaoImpl();
         return userDao.findByCode(code);
    }
    /*用户登录*/
    @Override
    public User login(String username, String password) {
        UserDao userDao=new UserDaoImpl();
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
/*判断用户是否被激活*/
    @Override
    public void isActive() {
        UserDao userDao=new UserDaoImpl();

    }



}
