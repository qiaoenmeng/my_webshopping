package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface RouteService {
    List<Route> findAllRoute(String cid);

    PageBean<Route> findAllPageBean(String cid, int pageNumber, int pageSize);

    PageBean<Route> findAllPageBeanByCnameAndCid(String cid, String searchword, int StartIndex, int pageSize);

    Route findRoute(String rid) throws InvocationTargetException, IllegalAccessException;//通过rid获得路线的详细信息

    List<List<Route>> findRouteByPresentation();

    List<Route> findRouteByCount();

    List<Route> findRouteByTem();

    List<Route> findRouteByDate();

    PageBean<Route> findRouteByFavorite(String rname, String minprice, String maxprice, int pageNumber, int pageSize);


}