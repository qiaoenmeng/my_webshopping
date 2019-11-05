package com.itheima.dao.impl;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.dao.UserDao;
import com.itheima.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    /*
    * 添加数据
    * */
    public  void save(User user) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql="insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        Object[] params={
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        };
           jdbcTemplate.update(sql,params);
    }

  /*通过用户名
  * 判断用户是否存在*/
    public boolean exists(String username) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql="select count(*) from tab_user where username=?";
        int  count = jdbcTemplate.queryForObject(sql, int.class, username);
        return count>0;
    }
    /*
        通过激活码查找
    * */
    public int findByCode(String code) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" update tab_user set status= ?  where  code =?  ";
        int count = 0;
        try {
           //count = jdbcTemplate.update(sql, int.class, Constant.TRAVEL_USER_STATUS_1, code);
              count = jdbcTemplate.update(sql, Constant.TRAVEL_USER_STATUS_1, code);
        } catch (DataAccessException e) {

        }
        return count;
    }
/*
* 根据用户名  密码 查询用户  返回一个对象*/
    public User  findByUsernameAndPassword(String username, String password) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" select *from tab_user  where username= ? and password =?   ";
        User user=null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
        }
       // System.out.println(user.getUsername());
        return user;
    }


}
