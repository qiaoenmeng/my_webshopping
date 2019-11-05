package com.itheima.dao.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    /*查找导航条  返回一个集合 list*/
    @Override
    public List<Category> findCategory() {
        JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());

        String sql=" select *from tab_category ";
        List<Category> cList = null;
        try {
            cList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
        } catch (DataAccessException e) {

        }
        return cList;
    }
}
