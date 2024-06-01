package com.mine.service;

import com.mine.entity.Category;

import java.util.List;

public interface CategoryService {
    //增加分类
    void add(Category category);

    //查询分类
    List<Category> list();
    //编辑分类
    void update(Category category);
    //删除分类
    void delete(int id);
}
