package com.samzhou.controller;

import com.samzhou.pojo.Article;
import com.samzhou.pojo.PageBean;
import com.samzhou.pojo.Result;
import com.samzhou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result<String> add(@RequestBody @Validated  Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) String categoryId,
                                          @RequestParam(required = false) String state) {
        return Result.success(articleService.list(pageNum,pageSize,categoryId,state));
    }




}
