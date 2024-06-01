package com.mine.service.impl;

import com.mine.entity.Category;
import com.mine.mapper.CategoryMapper;
import com.mine.service.CategoryService;
import com.mine.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //增加分类
    @Override
    public void add(Category category) {
        //补充属性值
        Map<String,Object> map =ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        category.setCreateUser(userId);

        categoryMapper.add(category);

    }
    //查询分类
    @Override
    public List<Category> list() {
        return categoryMapper.select();
    }

    //编辑分类
    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }
    //删除分类
    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }


}
