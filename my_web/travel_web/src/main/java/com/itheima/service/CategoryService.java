package com.itheima.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itheima.domain.Category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();

    String findAllJson() throws JsonProcessingException;
}
