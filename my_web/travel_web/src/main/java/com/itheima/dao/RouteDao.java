package com.itheima.dao;

import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RouteDao {
    List<Route> findAllRouteByCid(String cid);

    int findTotalRecordBycid(String cid);

    List<Route> findByPage(String cid, int startIndex, int pageSize);

    int findTotalRecordBycidandRname(String cid, String searchword);

    List<Route> findPageByCidAndRname(String cid, String searchword, int startIndex, int pageSize);

    Map<String, Object> findRouteByRid(String rid);

    List<RouteImg> findImgListByRid(String rid);

    void updateCount(String rid, JdbcTemplate jdbcTemplate);

    List<Route> findRouteByCount();//查找人气前4的route

    List<Route> findRouteByRdata();//找出最新的route

    List<Route> findRouteByIsThemeTour();//查找主题旅游

    int findTotalRecordFavorite(String data, String minprice, String maxprice);

    List<Route> findPageByFavorite(String rname, String minprice, String maxprice, int startIndex, int pageSize);

    void delCount(String rid) throws SQLException;
}
