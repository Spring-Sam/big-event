package com.samzhou.service.impl;

import com.samzhou.mapper.CategoryMapper;
import com.samzhou.pojo.Category;
import com.samzhou.service.CategoryService;
import com.samzhou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public void add(Category category) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userid = Integer.valueOf(claims.get("id").toString()) ;
        category.setCreateUser(userid);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userid = Integer.valueOf(claims.get("id").toString()) ;
        return categoryMapper.list(userid);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
