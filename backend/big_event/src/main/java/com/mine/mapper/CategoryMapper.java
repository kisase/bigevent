package com.mine.mapper;

import com.mine.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values (#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    //查询分类类别
    @Select("select * from category ")
    List<Category> select();
    //编辑分类
    @Update("update category set category_name =#{categoryName},category_alias =#{categoryAlias}," +
            "update_time=now() where id=#{id}")
    void update(Category category);
    //删除分类
    @Delete("delete from category where id=#{id}")
    void delete(int id);
}
