package com.samzhou.service;

import com.samzhou.pojo.Article;
import com.samzhou.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);
}
