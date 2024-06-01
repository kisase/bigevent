package com.mine.service;

import com.mine.entity.User;

import java.util.Map;

public interface UserService {

    //查询用户名
    User findByUsername(String username);

    //注册
    void register(String username, String password);

    //更信息用户信息
    void update(User user);

    //更新用户头像
    void updateAvatar(String avatarUrl);

    //获取头像
    String getAvatar();

    //更新用户密码
    void updatePwd(String newPwd);

}
