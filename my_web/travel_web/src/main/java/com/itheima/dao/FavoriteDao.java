package com.itheima.dao;

import com.itheima.domain.Route;
import com.itheima.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;

public interface FavoriteDao {
    void addFavoriteByRid(String rid, User user, JdbcTemplate jdbcTemplate);

    boolean findIsFavoriteByRidAndUid(String rid, int uid);

    int findTotalRecordMyFavoriteByUid(int uid);

    List<Route> findMyFavoriteByPage(int uid, int startIndex, int pageSize);

    void delFavoriteByRid(String rid, User user) throws SQLException;
}
