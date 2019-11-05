package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.service.RouteService;
import com.itheima.service.impl.RouteServiceImpl;
import com.itheima.web.base.servlet.BaseServlet;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@WebServlet(name = "routeServlet",urlPatterns = "/routeServlet")
public class routeServlet extends BaseServlet {
    /*通过rid 获得某个路线的实体类*/
    public void FindRouteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1。获得数据
              //获得路线id 通过路线id 获得route表的信息 通过多表连接获得 商家信息  和图片信息 一起返回 组成一个新的表

            String rid = request.getParameter("rid");
            RouteService routeService=new RouteServiceImpl();
            Route route=routeService.findRoute(rid);

            //2处理数据
               //转换为json
            String json = new ObjectMapper().writeValueAsString(route);
            //3响应结果
                //发送给浏览器
            System.out.println(json);
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void findAllRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

              int pageNumber=1;

            //1。获得数据
            //获得cid 通过cid 可以找到 相同 标签下的所有 route
            String cid = request.getParameter("cid");
            String pageNumberStr = request.getParameter("pageNumber");
            //获得搜索框的内容
            String searchword = request.getParameter("searchword");
            try {//防止pageNumberStr为非数字情况
                pageNumber = Integer.valueOf(pageNumberStr);
                if(pageNumber<1){
                    pageNumber=1;
                }
            } catch (NumberFormatException e) {
            }

            RouteService routeService=new RouteServiceImpl();


            int pageSize=5;
            /*返回PageBean*/
             //PageBean<Route> pageList=routeService.findAllPageBean(cid,pageNumber,pageSize);
            PageBean<Route> pageList=routeService.findAllPageBeanByCnameAndCid(cid,searchword,pageNumber,pageSize);
            //System.out.println(pageList.getData());
            //2.处理数据
            //System.out.println(pageList.getPageNumber());
            String json = new ObjectMapper().writeValueAsString(pageList);
            //3.响应数据


            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*首页 通过  人气旅游  最新旅游  主题旅游    查看路线*/
    public void findRouteByPresentation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获得数据

            //处理数据
            RouteService routeService=new RouteServiceImpl();
            List< List<Route>> list=routeService.findRouteByPresentation();
            String json = new ObjectMapper().writeValueAsString(list);
            System.out.println("人气"+list.get(0));
            System.out.println("最新"+list.get(1));
            System.out.println("主题"+list.get(2));

            //响应数据
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /*最新*/
   public void findRouteByDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           RouteService routeService=new RouteServiceImpl();
           List<Route> list=routeService.findRouteByDate();
           //1.获得数据
           String json = new ObjectMapper().writeValueAsString(list);
           //3.响应数据

         // System.out.println("最新"+json);
           response.getWriter().print(json);

       } catch (Exception e) {
           e.printStackTrace();
       }

   }

    //人气
    public void findRouteByCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RouteService routeService=new RouteServiceImpl();
            List<Route> list=routeService.findRouteByCount();
            //1.获得数据
            String json = new ObjectMapper().writeValueAsString(list);

            //3.响应数据


            response.getWriter().print(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //主题
   public void findRouteByTem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try {
           RouteService routeService=new RouteServiceImpl();
           List<Route> list=routeService.findRouteByTem();
           //1.获得数据
           String json = new ObjectMapper().writeValueAsString(list);
           //3.响应数据
          //System.out.println("主题"+json);

           response.getWriter().print(json);
       } catch (Exception e) {
           e.printStackTrace();
       }

   }

     /*收藏排行榜*/
  public void FindRouteByFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
          //1.获得数据
          //System.out.println("我的收藏帮  serrvlet");
          int pageNumber=1;
          String pageNumberStr = request.getParameter("pageNumber");
          String searchData = request.getParameter("searchData");


          String minprice = request.getParameter("minprice");
          String maxprice = request.getParameter("maxprice");
          RouteService routeService=new RouteServiceImpl();
          try {//防止pageNumberStr为非数字情况
              pageNumber = Integer.valueOf(pageNumberStr);
              if(pageNumber<1){
                  pageNumber=1;
              }
          } catch (NumberFormatException e) {
          }
          int pageSize=8;
          PageBean<Route> pageBean=routeService.findRouteByFavorite(searchData,minprice,maxprice,pageNumber,pageSize);

          String json = new ObjectMapper().writeValueAsString(pageBean);
          //3.响应数据
          //
                   response.getWriter().print(json);

          //System.out.println("收藏榜"+json);
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
