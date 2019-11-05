package com.itheima.dao.impl;

import com.itheima.dao.FavoriteDao;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.impl.FavoriteServiceImpl;
import com.itheima.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    /*添加到我的收藏*/
    @Override
    public void addFavoriteByRid(String rid, User user, JdbcTemplate jdbcTemplate) {

        String sql = " insert into tab_favorite values(?, now() , ?) ";
        jdbcTemplate.update(sql , rid , user.getUid());

    }
   /*查看用户是否被用户收藏了*/
    @Override
    public boolean findIsFavoriteByRidAndUid(String rid, int uid) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" select count(*) from tab_favorite where rid=? and uid=? ";
        int count=0;
        try {
             count = jdbcTemplate.queryForObject(sql, int.class, rid, uid);
        } catch (DataAccessException e) {

        }
    return count>0;
    }
/*所有收藏的数量*/
    @Override
    public int findTotalRecordMyFavoriteByUid(int uid) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" select count(*) from tab_favorite where  uid=? ";
        int count=0;
        try {
            count = jdbcTemplate.queryForObject(sql, int.class,uid);
        } catch (DataAccessException e) {

        }

        return count;
    }
/*每页收藏的具体数据*/
    @Override
    public List<Route> findMyFavoriteByPage(int uid, int startIndex, int pageSize) {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" select *from tab_route r ,tab_favorite f where  r.rid=f.rid and uid=?";
        List<Route> routeList=null;
        try {
            routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), uid);
        } catch (DataAccessException e) {

        }
        return  routeList;
    }

    /**取消收藏
     * 将路线从我的搜藏移除
     * @param rid
     * @param user
     */

    @Override
    public void delFavoriteByRid(String rid, User user) throws SQLException {
        Connection connection = FavoriteServiceImpl.local.get();
       //语句执行者
        Statement statement = connection.createStatement();

        String sql = " delete from tab_favorite where rid ="+rid+" and uid ="+user.getUid();

        statement.executeUpdate(sql);

    }
}
