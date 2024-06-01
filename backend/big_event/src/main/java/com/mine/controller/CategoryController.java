package com.mine.controller;

import com.mine.entity.Category;
import com.mine.entity.Result;
import com.mine.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin //支持跨域
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //增加分类类别
    @PostMapping("/add")
    public Result add(@RequestBody @Validated Category category){
        categoryService.add(category);
        return Result.success();
    }

    //获取文章分类
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs =categoryService.list();
        return Result.success(cs);
    }

    //编辑文章分类
    @PostMapping("/update")
    public Result updata(@RequestBody @Validated Category category){
        categoryService.update(category);
        return Result.success();
    }

    //删除文章分类
    @PutMapping("/delete")
    public Result delete(int id){
        categoryService.delete(id);
        return Result.success();
    }
}
