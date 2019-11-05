package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface FavoriteService {
    void addFavorite(String rid,User user) throws SQLException;

    boolean isFavorite(String rid, int uid);

    PageBean<Route> findMyFavorite(int uid, int pageNumber, int pageSize);

    void delFavorite(String rid, User user) throws SQLException;
}
