package com.mine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor//有参构造
public class PageBean <T>{
    private long total;//总数
    private List<T> items;//当前页数的集合
}
