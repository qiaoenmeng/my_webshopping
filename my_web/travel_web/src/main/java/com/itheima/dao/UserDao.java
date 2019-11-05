package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {
    boolean exists(String username);

    void save(User user);

    int findByCode(String code);

    User findByUsernameAndPassword(String username, String password);


}
