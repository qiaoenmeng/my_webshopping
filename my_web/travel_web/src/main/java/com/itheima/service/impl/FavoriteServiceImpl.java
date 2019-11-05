package com.itheima.service.impl;

import com.itheima.dao.FavoriteDao;
import com.itheima.dao.RouteDao;
import com.itheima.dao.impl.FavoriteDaoImpl;
import com.itheima.dao.impl.RouteDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.FavoriteService;
import com.itheima.utils.JdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    /*添加收藏*/
    @Override
    public void addFavorite(String rid,User user) throws SQLException {
        //1.添加收藏次数
        //2.将商品添加到我的收藏
        //需要事务  前提同一资源
           //1.获得数据源
        DataSource dataSource = JdbcUtils.getDataSource();
        //初始化事务 --通知jdbc模版需要使用事务
        TransactionSynchronizationManager.initSynchronization();
        //创建模版对象
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        //获得连接 获得跟模版对象 同样的连接
        Connection connection = DataSourceUtils.getConnection(dataSource);
        //先获得连接
        try{
            //设置不自动提交
            connection.setAutoCommit(false);
            //操作
            FavoriteDao favoriteDao= new FavoriteDaoImpl();
            favoriteDao.addFavoriteByRid(rid,user,jdbcTemplate);//增加我的收藏

            RouteDao routeDao=new RouteDaoImpl();
            //修改收藏次数
            routeDao.updateCount(rid,jdbcTemplate);
            //提交事务
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            //3.回滚事务
            connection.rollback();
            //将错误抛出给servlet处理
            throw  new RuntimeException("发生了未知异常错误:"+e.getMessage());
        }finally {
            //4.关闭资源
            //释放连接
            TransactionSynchronizationManager.clearSynchronization();
            //设置连接自动提交
            connection.setAutoCommit(true);
        }
    }
   /*通过  rid  和 uid 判断用户是否添加了收藏*/
    @Override
    public boolean isFavorite(String rid, int uid) {
        FavoriteDao favoriteDao= new FavoriteDaoImpl();
        boolean flag = favoriteDao.findIsFavoriteByRidAndUid(rid, uid);
        return flag;
    }
    /*查找所有的收藏  */
    @Override
    public PageBean<Route> findMyFavorite(int uid, int pageNumber, int pageSize) {
        //1.创建对象  pagebean
        PageBean<Route> pageBean=new PageBean<>(pageNumber,pageSize);
        //查找
        FavoriteDao favoriteDao= new FavoriteDaoImpl();
        int totalRecord= favoriteDao.findTotalRecordMyFavoriteByUid(uid);//收藏的总数量
        List<Route> data=favoriteDao.findMyFavoriteByPage(uid,pageBean.getStartIndex(),pageSize);
        //3.赋值
        pageBean.setTotalRecord(totalRecord);//获得所有收藏的数量
        pageBean.setData(data);//获得每一页的收藏
        //2.返回结果
        return pageBean;
    }
    /*取消收藏*/
    //线程池
    public static final ThreadLocal<Connection> local =new ThreadLocal<>();
    @Override
    public void delFavorite(String rid, User user) throws SQLException {
            //1.减少商品的收藏次数
            //2.将商品从我的收藏移除
            //3需要事务
        //获得连接
        Connection connection =JdbcUtils.getConnection();
        //将连接和线程绑定在一起
        local.set(connection);
        try{
            //设置不自动提交事务
            connection.setAutoCommit(false);
            //操作
            FavoriteDao favoriteDao= new FavoriteDaoImpl();
            favoriteDao.delFavoriteByRid(rid,user);//增加我的收藏

            RouteDao routeDao=new RouteDaoImpl();
            //修改收藏次数
            routeDao.delCount(rid);
            //提交事务
            connection.commit();

        }catch (Exception e){
            e.printStackTrace();
           //回滚事务
            connection.rollback();
        }finally {
            connection.close();
        }

    }
}
