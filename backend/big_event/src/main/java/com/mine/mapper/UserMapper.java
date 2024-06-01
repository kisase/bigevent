package com.mine.mapper;

import com.mine.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //用户查询
    @Select("select *from user where username=#{username}")
    public User findByUsername(String username);

    //注册
    @Insert("insert into user(username,password,create_time,update_time)"+
            "values(#{username},#{password},now(),now())")
    void add(String username,String password);
    //更新用户信息
    @Update("update user set nickname =#{nickname},email=#{email},update_time =now() where id=#{id};")
    void update(User user);

    //更新头像
    @Update("update user set user_pic =#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    //获取头像url

    // 更新密码
    @Update("update user set password =#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd, Integer id);

    @Select("select user_pic from user where id=#{id}")
    String getAvatar(int id);
}
