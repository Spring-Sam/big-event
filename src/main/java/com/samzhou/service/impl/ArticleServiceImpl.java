package com.samzhou.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.samzhou.mapper.ArticleMapper;
import com.samzhou.pojo.Article;
import com.samzhou.pojo.PageBean;
import com.samzhou.service.ArticleService;
import com.samzhou.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer currentUserId  = Integer.valueOf(claims.get("id").toString());
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateUser(currentUserId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer currentUserId  = Integer.valueOf(claims.get("id").toString());
        List<Article> as = articleMapper.list(categoryId,state,currentUserId);
        //Page提供方法，获取PageHelper的总数和记录
        Page<Article> page = (Page<Article>) as;

        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }
}
