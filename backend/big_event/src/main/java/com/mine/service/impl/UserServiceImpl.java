package com.mine.service.impl;

import com.mine.entity.User;
import com.mine.mapper.UserMapper;
import com.mine.service.UserService;
import com.mine.utils.MD5Util;
import com.mine.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    //在mapper层调用Usermapper对象
    @Autowired
    private UserMapper userMapper;
    //用户名验证
    @Override
    public User findByUsername(String username) {
        User u= userMapper.findByUsername(username);
        return u;
    }

    //用户添加
    @Override
    public void register(String username, String password) {
        //加密
        String md5String = MD5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }

    //用户信息更新
    @Override
    public void update(User user) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
//        user.setUpdateTime(LocalDateTime.now());
        user.setId(id);
        userMapper.update(user);

    }

    //更新头像
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }
    //获取头像
    @Override
    public String getAvatar() {
        Map<String, Object> map =ThreadLocalUtil.get();
        int id =(int) map.get("id");
       String file= userMapper.getAvatar(id);
        return file;
    }
    //更新密码
    @Override
    public void updatePwd(String newPwd) {
        String password = MD5Util.getMD5String(newPwd);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(password,id);

    }

}
