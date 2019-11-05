package com.itheima.dao.impl;

import com.itheima.dao.RouteDao;
import com.itheima.domain.Route;


import com.itheima.domain.RouteImg;
import com.itheima.service.impl.FavoriteServiceImpl;
import com.itheima.utils.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements RouteDao {
    /*通过cid 查找所有路线 信息
    返回值 List<Route>
    * */
    @Override
    public List<Route> findAllRouteByCid(String cid) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route where cid=? ";
        System.out.println(cid);
      // int i = Integer.parseInt(cid);
       Object[] params={cid};
        List<Route> rList=null;
        try {
          //  rList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),i);
            rList= jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params);

        } catch ( Exception e) {

        }
        System.out.println(rList);
        return rList;
    }
/*  根据cid查找总记录数
返回值  int totalRecord
* */
    @Override
    public int findTotalRecordBycid(String cid) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT count(*) FROM tab_route where cid=? ";
       // System.out.println(cid);
        // int i = Integer.parseInt(cid);
        Object[] params={cid};
        int totalRecord=0;
        try {
            //  rList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),i);
            totalRecord = jdbcTemplate.queryForObject(sql, int.class, params);

        } catch ( Exception e) {

        }

        return totalRecord;
    }
/* 根据 cid分页查询
*   返回值为  每页的数据*/
    @Override
    public List<Route> findByPage(String cid, int startIndex, int pageSize) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route where 1=1 ";
        //用于拼接参数
        List<Object> params=new ArrayList<Object>();
         //cid不能够为null或则空格
        if (!StringUtils.isBlank(cid)) {
            sql+=" and cid = ? " ;
            params.add(cid);
        }
        params.add(startIndex);
        params.add(pageSize);
        sql+=" limit ?,? ";
        List<Route> pageList=null;
        try {
             pageList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        } catch (DataAccessException e) {

        }
       // System.out.println(pageList);
        return pageList;
    }

    /**
     * 通过 cid 和 rname 查询总数据
     返回值  总数据
     * @param cid
     * @param searchword
     * @return
     */
    @Override
    public int findTotalRecordBycidandRname(String cid, String searchword) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT count(*) FROM tab_route where 1=1 ";

        List<Object> params=new ArrayList<>();
        if (!StringUtils.isBlank(cid)){
            sql+="  and cid=? ";
            params.add(cid);
        }
        if (!StringUtils.isBlank(searchword)){
            sql+="  and rname like ? ";
            searchword="%"+searchword+"%";
            params.add(searchword);
        }
       // Object[] params={cid};
        int totalRecord=0;
        try {
            //  rList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),i);
            totalRecord = jdbcTemplate.queryForObject(sql, int.class, params.toArray());

        } catch ( Exception e) {

        }

        return totalRecord;
    }

    /*通过 cid 和 rname   进行分页查询
    * 返回值  每页路线 list*/
    @Override
    public List<Route> findPageByCidAndRname(String cid, String searchword, int startIndex, int pageSize) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route where 1=1 ";
        //用于拼接参数
        List<Object> params=new ArrayList<Object>();
        //cid不能够为null或则空格
        if (!StringUtils.isBlank(cid)) {
            sql+=" and cid = ? " ;
            params.add(cid);
        }
        if (!StringUtils.isBlank(searchword)) {
            sql+=" and rname like ? " ;
            searchword="%"+searchword+"%";
            params.add(searchword);
        }
        params.add(startIndex);
        params.add(pageSize);
        sql+=" limit ?,? ";
        List<Route> pageList=null;
        try {
            pageList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        } catch (DataAccessException e) {

        }

        return pageList;
    }
/*查看一条线路的详细信息 包括商家**/
    @Override
    public  Map<String, Object> findRouteByRid(String rid) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT *FROM tab_route  r,tab_seller s,tab_category c WHERE  r.sid=s.sid  and r.cid=c.cid AND rid=? ";
        int rid1 = 0;
        try {
            rid1 = Integer.valueOf(rid);
        } catch (NumberFormatException e) {

        }
        Object[] params={rid1};
        Map<String, Object> routeMap = null;
        try {
            routeMap = jdbcTemplate.queryForMap(sql, params);
        } catch (DataAccessException e) {

        }
      //  System.out.println("routeMap"+routeMap);
        return routeMap;
    }
/*通过rid 找到改路线包含的图片信息  返回
* */
    @Override
    public List<RouteImg> findImgListByRid(String rid) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql="  SELECT i.rgid,i.rid,i.bigPic,i.smallPic  FROM tab_route r,tab_route_img  i WHERE r.rid=i.rid AND r.rid=?  ";
        List<RouteImg> routeImgs = null;
        try {
            routeImgs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
       // System.out.println("img"+routeImgs);
        return routeImgs;
    }
/*添加收藏次数*/
    @Override
    public void updateCount(String rid, JdbcTemplate jdbcTemplate) {
        String sql=" update tab_route set count=count+1 where rid= ? ";
        jdbcTemplate.update(sql, rid);

    }
/*人气旅游*/
    @Override
    public List<Route> findRouteByCount() {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route   ORDER BY  COUNT DESC LIMIT 0,4 ";
        List<Route> routeList=null;
        try {
             routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {

        }
        return routeList;
    }
/*最新旅游*/
    @Override
    public List<Route> findRouteByRdata() {

        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route   ORDER BY  rdate DESC LIMIT 0,4 ";
        List<Route> routeList=null;
        try {
            routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {

        }
        return routeList;
    }
/*主题旅游*/
    @Override
    public List<Route> findRouteByIsThemeTour() {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql="  SELECT * FROM tab_route   ORDER BY  isThemeTour DESC  LIMIT 0,4 ";
        List<Route> routeList=null;
        try {
            routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class));
        } catch (DataAccessException e) {

        }
        return routeList;
    }
/*收藏排行榜  总数量*/
    @Override
    public int findTotalRecordFavorite(String rname, String minprice, String maxprice) {


        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT count(*) FROM tab_route where 1=1 ";

        List<Object> params=new ArrayList<>();
        //cid不能够为null或则空格
        if (!StringUtils.isBlank(rname)) {
            sql+=" and rname like ? " ;
            rname="%"+rname+"%";
            params.add(rname);
        }
        if (!StringUtils.isBlank(minprice)) {
            sql+=" and  price >= ? " ;
            params.add(minprice);
        }
        if (!StringUtils.isBlank(maxprice)) {
            sql+=" and  price <=? " ;
            params.add(maxprice);
        }
        int totalRecord=0;
        try {
            //  rList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class),i);
            totalRecord = jdbcTemplate.queryForObject(sql, int.class, params.toArray());

        } catch ( Exception e) {

        }

        return totalRecord;
    }
    /*收藏排行榜分页查询 */

    @Override
    public List<Route> findPageByFavorite(String rname, String minprice, String maxprice, int startIndex, int pageSize) {
        JdbcTemplate jdbcTemplate= new JdbcTemplate(JdbcUtils.getDataSource());
        String sql=" SELECT * FROM tab_route where 1=1 ";
        //用于拼接参数
        List<Object> params=new ArrayList<Object>();
        //cid不能够为null或则空格
        if (!StringUtils.isBlank(rname)) {
            sql+=" and rname like ? " ;
            rname="%"+rname+"%";
            params.add(rname);
        }
        if (!StringUtils.isBlank(minprice)) {
            sql+=" and price >= ? " ;
            params.add(minprice);
        }
        if (!StringUtils.isBlank(maxprice)) {
            sql+=" and price <= ? " ;
            params.add(maxprice);
        }
        params.add(startIndex);
        params.add(pageSize);
        sql+=" limit ?,? ";
        List<Route> pageList=null;
        try {
            pageList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
        } catch (DataAccessException e) {

        }
        return pageList;
    }

    /**
     * 取消收藏
     * 将路线的收藏次数减一
     * @param rid
     */
    @Override
    public void delCount(String rid) throws SQLException {
        Connection connection = FavoriteServiceImpl.local.get();
        //语句执行者
        Statement statement = connection.createStatement();
        String sql="update tab_count set count=count-1 where rid="+rid;
        statement.executeUpdate(sql);
    }
}
