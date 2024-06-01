package com.mine.controller;

import com.mine.entity.Result;
import com.mine.entity.User;
import com.mine.service.UserService;
import com.mine.utils.JWTUtil;
import com.mine.utils.MD5Util;
import com.mine.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/user")
@CrossOrigin //支持跨域
public class UserController {
    @Autowired
    private UserService userService;

    //注册校验
    @PostMapping("/register")
    //全局校验
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        //判断用户是否存在，如果不存在，进行数据响应；如果存在，返回提示词
        User u = userService.findByUsername(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用！");
        }
    }

    //登录校验
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User loginUser = userService.findByUsername(username);
        //判断用户是否存在，如果不存在，则返回提示词；如果存在，进行数据响应
        if (loginUser == null) {
            return Result.error("用户名错误！");
        }

        //判断用户密码是否正确
        if (MD5Util.getMD5BlackString(password, loginUser.getPassword())) {
            //登陆成功
            //生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JWTUtil.getToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误！");
    }

    //查询用户信息
    @GetMapping("/userInfo")
    public Result<User> userIfo() {
        //根据用户名查询用户
//        Map<String, Object> map = JWTUtils.parseToken(token);
//        String username = (String) map.get("username");

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    //修改用户头像
    //传递URL地址
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    //修改用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        //校验参数
        String oldPwd = params.get("old_Pwd");//旧密码
        String newPwd = params.get("new_Pwd");//新密码
        String rePwd = params.get("re_Pwd");// 确认密码

        if (!StringUtils.hasLength("oldPwd") ||
                !StringUtils.hasLength("newPwd") ||
                !StringUtils.hasLength("rePwd")) {
            return Result.error("缺少必要的参数！");
        }

        //原密码是否正确
        //调用userService根据用户名拿到密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginuser = userService.findByUsername(username);
        if (!MD5Util.getMD5BlackString(oldPwd, loginuser.getPassword())) {
            return Result.error("原密码填写不正确！");
        }

        //newPwd和rePwd密码是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次填写的密码不一致！");
        }
        //调用service完成密码更新
        userService.updatePwd(newPwd);

        return Result.success();
    }
}
