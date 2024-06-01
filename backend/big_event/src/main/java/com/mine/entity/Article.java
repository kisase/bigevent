package com.mine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
@Data
public class Article {

    private Integer id;//主键
    @NotEmpty
    @Pattern(regexp = "\\S{1,20}$")
    private String title;//文章标题
    @NotEmpty
    private String content;//文章内容
    @URL
    private String coverImg;//封面图地址
    private String state; //文章状态 发布||草稿
    private int categoryId;//分类ID

    private int createUser;//创建者ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
