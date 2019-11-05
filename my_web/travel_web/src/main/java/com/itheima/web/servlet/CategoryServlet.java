package com.itheima.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.UserService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.service.impl.UserServiceimpl;
import com.itheima.web.base.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    /*找到所有导航条*/
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CategoryService categoryService=new CategoryServiceImpl();

            List<Category> cList=categoryService.findAllCategory();

            //响应结果
            String json = new ObjectMapper().writeValueAsString(cList);
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*将导航条存入缓从缓存中获得数据*/
    public void findAllCategoryJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CategoryService categoryService=new CategoryServiceImpl();
            String json=categoryService.findAllJson();
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
