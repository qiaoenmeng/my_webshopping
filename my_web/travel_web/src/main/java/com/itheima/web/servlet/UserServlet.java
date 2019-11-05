package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constant.Constant;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceimpl;
import com.itheima.utils.UuidUtil;
import com.itheima.web.base.servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {

  /*检查是否登陆*/
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=null;
        try {
            User user = (User)request.getSession().getAttribute("user");
            if (user==null){//未登录
                resultInfo =new ResultInfo(true,"","请登录");

            }else{//登陆了
                resultInfo =new ResultInfo(true,user.getName(),"");

            }
        } catch (Exception e) {
           // e.printStackTrace();
                resultInfo =new ResultInfo(false,"","出现未知异常");
        }
        //响应数据  到浏览器
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }

    /*登陆*/
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=null;
        try {
            //1.获取数据
            //1.1 用户名  密码
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // 进入service dao 判断用户是否纯在   select *from tab_user  where username= password=
            UserService userService=new UserServiceimpl();
            User user= userService.login(username, password);
            //2.处理数据
            // 2.1用户名不存在 null
              //判断  用户是否存在
            if (user==null){//不存在
                resultInfo=new ResultInfo(true,"","该用户不存在");
                //System.out.println("用户不存在");
            }else {//存在
                //1.激活
                if (user.getStatus().equals(Constant.TRAVEL_USER_STATUS_1)){//登陆成功 返回首页
                    //System.out.println("用户存在");
                    //resultInfo=new ResultInfo(true,"","该用户不存在");
                    //将user信息存入session中使得资源共享
                    resultInfo = new ResultInfo( true ,true , "");
                    request.getSession().setAttribute("user",user);
                    //response.sendRedirect("index.html");

                }else{   //2.未激活
                   resultInfo=new ResultInfo(true,false,"未激活，激活码");//false 代表未激活
                    //System.out.println("未激活，激活码");
                }

            }

        } catch (Exception e) {
            //e.printStackTrace();
            //异常返回
            resultInfo = new ResultInfo( false  ,"" , "未知异常错误");
        }
        //3.响应数据
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);

    }

    /*激活*/
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=null;
        try {
            //获取激活码
            String code = request.getParameter("code");
           //判断用户是否激活过了
            //如果激活过了
            //则
            //如果没有
            //激活
            //激活

            UserService userService=new UserServiceimpl();
            //userService.isActive();
            int count = userService.active(code);
            if (count>0){//激活成功
              response.sendRedirect("index.html");//跳转首页
            }else{//未激活成功
                response.getWriter().print("激活失败");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



/*
* 注册*/
   public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=null;

        try {
            //1.获取数据
            //1.1获取数据的map集合
            Map<String, String[]> parameterMap = request.getParameterMap();
            //1.2创建user对象
            User user=new User();
            user.setStatus(Constant.TRAVEL_USER_STATUS_0);
            user.setCode(UuidUtil.getUuid());//设置激活码
            System.out.println(UuidUtil.getUuid());
            //1.3将数据封装成对象形式
            BeanUtils.populate(user,parameterMap);
            //2.处理数据
            //添加到数据库
            UserService userService=new UserServiceimpl();//多态
            userService.rejister(user);
            //3 响应结果
            resultInfo=new ResultInfo(true,"","");//响应给页面的信息
        } catch (Exception e) {
            String message = e.getMessage();//获取异常信息
            resultInfo=new ResultInfo(false,"",message);
        }

        //将json信息发送
        String json = new ObjectMapper().writeValueAsString(resultInfo);
        response.getWriter().print(json);
    }


    /*退出系统*/
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try{
          //清除user资源
        request.getSession().removeAttribute("user");
          //System.out.println("退出");
        //跳转首页
          response.sendRedirect("index.html");

      }catch (Exception e){
          e.printStackTrace();
      }
    }
}
