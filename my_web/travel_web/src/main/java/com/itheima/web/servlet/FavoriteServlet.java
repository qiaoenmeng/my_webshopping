package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.PageBean;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.FavoriteService;
import com.itheima.service.RouteService;
import com.itheima.service.impl.FavoriteServiceImpl;
import com.itheima.service.impl.RouteServiceImpl;
import com.itheima.web.base.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FavoriteServlet",urlPatterns = "/FavoriteServlet")
public class FavoriteServlet extends BaseServlet {
    /*点击收藏*/
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=null;
        try {
            //1.获得数据 user,rid
            User user = (User)request.getSession().getAttribute("user");
            String rid = request.getParameter("rid");
            //如果user null 登录页面
            if (user==null){

                resultInfo = new ResultInfo(true, 0 , "");
            }else{
             //否则 通过 rid 进入数据库 修改route 表的丑藏次数
                FavoriteService favoriteService=new FavoriteServiceImpl();
                favoriteService.addFavorite(rid,user);
                //获得搜藏次数
                RouteService routeService=new RouteServiceImpl();
                Route route = routeService.findRoute(rid);
              //响应数据
               resultInfo = new ResultInfo(true, route.getCount() , "");
            }

            //2.处理数据
            //3.响应结果
        } catch (Exception e) {
            resultInfo=new ResultInfo(false,"","未知异常");

        }
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

   /*判断是否收藏了商品*/
   public void isExistsFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       ResultInfo resultInfo=null;
       try {
           //1.获得数据 user,rid
           User user = (User)request.getSession().getAttribute("user");
           String rid = request.getParameter("rid");
           //如果user null
           if (user==null){//未登录
               //resultInfo=new ResultInfo(true,false,"");
           }else{//登陆成功  判断是否添加了收藏
               FavoriteService favoriteService=new FavoriteServiceImpl();
               //
               boolean flag=favoriteService.isFavorite(rid,user.getUid());
               response.getWriter().print(flag);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

  /*查看我的收藏*/
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获得数据
            User user = (User)request.getSession().getAttribute("user");
            String pageNumberStr = request.getParameter("pageNumber");
            int pageNumber=1;

            try {
                pageNumber=Integer.parseInt(pageNumberStr);
                if (pageNumber<1){
                    pageNumber=1;
                }
            } catch (NumberFormatException e) {

            }
            int pageSize=12;
            //通过user.uid查找数据
            FavoriteService favoriteService=new FavoriteServiceImpl();
            PageBean<Route> pageBean=favoriteService.findMyFavorite(user.getUid(),pageNumber,pageSize);


            //处理数据
            String json = new ObjectMapper().writeValueAsString(pageBean);
            //响应数据
            System.out.println(json);
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 点击取消收藏
     */

   public void delFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       ResultInfo resultInfo=null;
       try {
           //1.获得数据 user,rid
           User user = (User)request.getSession().getAttribute("user");
           String rid = request.getParameter("rid");
           //如果user null 登录页面
           if (user==null){

               resultInfo = new ResultInfo(true, 0 , "");
           }else{
               //否则 通过 rid 进入数据库 修改route 表的收藏次数
               FavoriteService favoriteService=new FavoriteServiceImpl();
               favoriteService.delFavorite(rid,user);
               //获得搜藏次数
               RouteService routeService=new RouteServiceImpl();
               Route route = routeService.findRoute(rid);
               //响应数据
               resultInfo = new ResultInfo(true, route.getCount() , "");
           }

           //2.处理数据
           //3.响应结果
       } catch (Exception e) {
           resultInfo=new ResultInfo(false,"","未知异常");

       }
       String json = new ObjectMapper().writeValueAsString(resultInfo);
       response.getWriter().print(json);
    }
}
