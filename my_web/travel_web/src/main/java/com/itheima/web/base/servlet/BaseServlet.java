package com.itheima.web.base.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 所有servlet的父类
 */
@WebServlet(name = "BaseServlet" , urlPatterns = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 以下代码 使用if条件判断不同的请求  执行不同的方法
         * 问题:
         * 1.请求多的话, if太长
         * 2.漏了某一个判断
         *
         * 代码相同的 可以进行抽取
         * 代码结构相同的 也可以进行抽取
         *
         * 拿到的是一个字符串 需要执行某个方法 ==>> 字符串的名称 跟方法名称一致
         * 反射: Method method =  class.getMethod("字符串")
         * 拿到方法对象  执行 method.invoke()
         * 需要反射:
         * 1.需要 类的Class对象   字节码对象 字节码对象中有 构造 方法 字段...
         * 2.需要执行方法的字符串  根据字节码对象 获得 字节码中的方法对象
         * 3.方法调用invoke执行
         */

        try {
            //1.获得方法的字符串
            String method = request.getParameter("method");//获得请求中真正携带的数据 用于判断执行的if条件

            //2.获得类的class对象  clazz 是字节码对象 是内存中的唯一对象
            Class clazz  = this.getClass();
            //System.out.println(clazz);

            //3.获得方法的对象
            //方法的对象
            Method invokeMethod = clazz.getMethod(method ,HttpServletRequest.class , HttpServletResponse.class );

            //4.执行对象
            //参数1 : 执行方法的对象
            //参数2 : 真实参数
            invokeMethod.invoke(this , request ,response);
            //哪个对象的哪个方法  以及真实参数
            //this.register(request,response);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
