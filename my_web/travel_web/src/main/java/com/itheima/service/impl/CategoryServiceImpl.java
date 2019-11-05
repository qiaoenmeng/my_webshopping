package com.itheima.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    /*查找所有导航条*/
    @Override
    public List<Category> findAllCategory() {


        CategoryDao categoryDao=new CategoryDaoImpl();

        List<Category> cList= categoryDao.findCategory();

        return  cList;
    }

    @Override
    public String findAllJson() throws JsonProcessingException {
        Jedis jedis = JedisUtil.getJedis();
        String json = jedis.get("cList");
        if (json==null){//未存入缓存
            //从数据库中查找
            List<Category> allCategory = findAllCategory();
            json = new ObjectMapper().writeValueAsString(allCategory);
            //将json存入缓存
            jedis.set(Constant.TRAVEL_CATEGORY_LIST_JSON , json);
        }

        return  json;
    }


}
