package com.itheima.service.impl;

import com.itheima.dao.impl.RouteDaoImpl;
import com.itheima.domain.*;
import com.itheima.dao.RouteDao;
import com.itheima.service.RouteService;
import org.apache.commons.beanutils.BeanUtils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements RouteService {
    /*查找所有路线*/
    @Override
    public List<Route> findAllRoute(String cid) {
        RouteDao routeDao=new RouteDaoImpl();
        List<Route>  rList= routeDao.findAllRouteByCid(cid);
        //System.out.println("2"+rList);
        return rList;
    }
/*分页查询
通过cid（导航条） 查询
* 返回值为 PageBean*/
    @Override
    public PageBean findAllPageBean(String cid, int pageNumber, int pageSize ) {
        //1.创建PageBean对象
        PageBean<Route> pageBean=new PageBean<>(pageNumber,pageSize);
        //3.给PageBean赋值
           //两查
           //3.1 totalrecord  总条数
        RouteDao routeDao=new RouteDaoImpl();
        int totalRecord = routeDao.findTotalRecordBycid(cid);
          //3.2 每页的数据 data
        List<Route> data=  routeDao.findByPage(cid,pageBean.getStartIndex(),pageSize);
       // System.out.println("service"+data);
        //4.赋值
          pageBean.setTotalRecord(totalRecord);
          pageBean.setData(data);
        // List<PageBean>  pageBeanList=null;
        //2.返回PageBean
        return pageBean;
    }
/*通过导航条cid   和  搜索框 cname
* 分页查询路线*/
    @Override
    public PageBean<Route> findAllPageBeanByCnameAndCid(String cid,String searchword, int pageNumber, int pageSize) {

        //1.创建PageBean对象
        PageBean<Route> pageBean=new PageBean<>(pageNumber,pageSize);
        //3.给PageBean赋值
        //两查
        //3.1 totalrecord  总条数
        RouteDao routeDao=new RouteDaoImpl();
        int totalRecord = routeDao.findTotalRecordBycidandRname(cid,searchword);
       // System.out.println("总记录数"+totalRecord);
        //3.2 每页的数据 data
        List<Route> data=  routeDao.findPageByCidAndRname(cid,searchword,pageBean.getStartIndex(),pageSize);
        // System.out.println("service"+data);
        //4.赋值
        pageBean.setTotalRecord(totalRecord);
        pageBean.setData(data);
        // List<PageBean>  pageBeanList=null;
        //2.返回PageBean
        return pageBean;
    }
  /* 获得一条路线的详细信息*/
    @Override
    public Route findRoute(String rid) throws InvocationTargetException, IllegalAccessException {
        //1.创建
        Route route=new Route();
        //4.获取
           RouteDao  routeDao=new RouteDaoImpl();
           /*获取route类属性的Map 值 封装数*/
          Map<String, Object> routeMap= routeDao.findRouteByRid(rid);

        Category category=new Category();
        Seller seller=new Seller();
        List<RouteImg> routeImgList=new ArrayList<>();
        //封装对象
        BeanUtils.populate(category, routeMap);
        BeanUtils.populate(seller,routeMap);
        BeanUtils.populate(route,routeMap);

        routeImgList=routeDao.findImgListByRid(rid);
        //3.f赋值
        route.setCategory(category);//所属分类
        route.setSeller(seller);//所属商家
        route.setRouteImgList(routeImgList);//商品详情图片列表
        //2.返回
        return route;
    }
/*首页 通过  人气旅游  最新旅游  主题旅游 获得 route*/
    @Override
    public  List< List<Route>> findRouteByPresentation() {
        RouteDao routeDao=new RouteDaoImpl();
        /*找出收藏量前4的route*/
        List<Route> list1=routeDao.findRouteByCount();
        /*找出最新的route*/
        List<Route> list2=routeDao.findRouteByRdata();
        /*查找主题旅游*/
        List<Route> list3=routeDao.findRouteByIsThemeTour();
        List< List<Route>> routeLists=new ArrayList<>();
        routeLists.add(list1);
        routeLists.add(list2);
        routeLists.add(list3);
        return routeLists;
    }
 //人气
    @Override
    public List<Route> findRouteByCount() {
        RouteDao routeDao=new RouteDaoImpl();
        /*找出收藏量前4的route*/
        List<Route> list1=routeDao.findRouteByCount();
        return list1;
    }
//主题
    @Override
    public List<Route> findRouteByTem() {
        /*查找主题旅游*/
        RouteDao routeDao=new RouteDaoImpl();
        List<Route> list=routeDao.findRouteByIsThemeTour();
       // System.out.println("/主题"+list);
        return list;
    }
//最新
    @Override
    public List<Route> findRouteByDate() {
        /*查找主题旅游*/
        RouteDao routeDao=new RouteDaoImpl();
        List<Route> list=routeDao.findRouteByRdata();
        //System.out.println("/最新"+list);

        return list;
    }
    /*收藏排行榜*/
    @Override
    public PageBean<Route> findRouteByFavorite(String rname, String minprice, String maxprice, int pageNumber, int pageSize) {
        //1.创建PageBean对象
        PageBean<Route> pageBean=new PageBean<>(pageNumber,pageSize);
        //3.给PageBean赋值
        //两查
        //3.1 totalrecord  总条数
        RouteDao routeDao=new RouteDaoImpl();
        int totalRecord = routeDao.findTotalRecordFavorite(rname,minprice,maxprice);
        // System.out.println("总记录数"+totalRecord);
        //3.2 每页的数据 data
        List<Route> data=  routeDao.findPageByFavorite(rname,minprice,maxprice,pageBean.getStartIndex(),pageSize);
        // System.out.println("service"+data);
        //4.赋值
        pageBean.setTotalRecord(totalRecord);
        pageBean.setData(data);
        // List<PageBean>  pageBeanList=null;
        //2.返回PageBean
        System.out.println(data);
        return pageBean;
    }




}
