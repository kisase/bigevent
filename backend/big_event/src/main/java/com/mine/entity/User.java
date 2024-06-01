package com.mine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class User {
    @NotNull
    private int id;//主键
    private String username;//用户名
    @JsonIgnore  //在json序列化时将password属性忽略掉
    private String password;//密码
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}")
    private String nickname;//昵称
    @NotEmpty
    @Email
    private String email;//邮箱
    private String userpic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间

}
