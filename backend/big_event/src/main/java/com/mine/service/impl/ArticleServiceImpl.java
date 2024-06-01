package com.mine.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mine.entity.Article;
import com.mine.entity.PageBean;
import com.mine.mapper.ArticleMapper;
import com.mine.service.ArticleService;
import com.mine.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service

public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    //文章添加
    @Override
    public void add(@RequestBody Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }
    //获取文章。条件分类列表查询
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String title, Integer categoryId) {
        //创建pageBean对象
        PageBean<Article> pb=new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用Mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId= (Integer) map.get("id");
        List<Article> as= articleMapper.list(userId,title,categoryId);
        //Page中提供了方法，可以获取PageHelper分页查询后看到总记录数和当前数据
        Page<Article> p =(Page<Article>) as;
        //把数据填充到PageBean对象
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb ;
    }
    //文章编辑
    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public PageBean<Article> AllList(Integer pageNum, Integer pageSize, String title, Integer categoryId) {
        //创建pageBean对象
        PageBean<Article> pb1=new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用Mapper

        List<Article> as= articleMapper.AllList(title,categoryId);
        //Page中提供了方法，可以获取PageHelper分页查询后看到总记录数和当前数据
        Page<Article> p =(Page<Article>) as;
        //把数据填充到PageBean对象
        pb1.setTotal(p.getTotal());
        pb1.setItems(p.getResult());
        return pb1 ;
    }

    @Override
    public Article findById(int id) {
        Article article = articleMapper.findById(id);

        return article;
    }

    //删除文章
    @Override
    public void delete(int id) {
        articleMapper.delete(id);
    }


}
