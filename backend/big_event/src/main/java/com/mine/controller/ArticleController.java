package com.mine.controller;

import com.mine.entity.Article;
import com.mine.entity.PageBean;
import com.mine.entity.Result;
import com.mine.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin //支持跨域
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //文章添加
    @PostMapping
    public Result addArticle(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

    //文章列表查询
    @GetMapping
    public Result<PageBean<Article>> list(
        Integer pageNum,
        Integer pageSize,
        @RequestParam(required = false)String title,
        @RequestParam(required = false)Integer categoryId //允许为空
    ) {
        PageBean<Article> pd = articleService.list(pageNum,pageSize,title,categoryId);
        return Result.success(pd);
    }

    @GetMapping("/published")
    public Result<PageBean<Article>> AllList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false)String title,
            @RequestParam(required = false)Integer categoryId //允许为空
    ) {
        PageBean<Article> pd = articleService.AllList(pageNum,pageSize,title,categoryId);
        return Result.success(pd);
    }

    //编辑文章
    @PostMapping("/update")
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();
    }

    //删除文章
    @PutMapping
    public Result delete(int id){
        articleService.delete(id);
        return Result.success();
    }
    @PostMapping("/findById")
    public Result findById(int id){
        Article article = articleService.findById(id);
        return Result.success(article);
    }
}
