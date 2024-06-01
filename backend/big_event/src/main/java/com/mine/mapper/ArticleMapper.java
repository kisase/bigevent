package com.mine.mapper;

import com.mine.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //文章添加
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user," +
            "create_time,update_time) values (#{title},#{content},#{coverImg},#{state}," +
            "#{categoryId},#{createUser},now(),now())")
    void add(Article article);

    List<Article> list(Integer userId, String title, Integer categoryId);

    //文章编辑
    void update(Article article);

    //文章删除
    @Delete("delete from article where id=#{id}")
    void delete(int id);


    List<Article> AllList(String title, Integer categoryId);
    @Select("select *from article where id=#{id}")
    Article findById(int id);
}
