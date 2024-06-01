package com.mine.service;

import com.mine.entity.Article;
import com.mine.entity.PageBean;

public interface ArticleService {
    //文章添加
    void add(Article article);
    //获取文章。条件分类列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, String title, Integer categoryIde);

    void delete(int id);

    void update(Article article);

    PageBean<Article> AllList(Integer pageNum, Integer pageSize, String title, Integer categoryId);

    Article findById(int id);
}
