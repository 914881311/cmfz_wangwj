package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.HashMap;

public interface ArticleService {
    //展示全部
    HashMap<String,Object> queryByPage(Integer page, Integer rows);
    //删除
    HashMap<String, Object> delete(Article article);
    //添加
    HashMap<String,Object> add(Article article);
    //修改
    HashMap<String,Object> update(Article article);
}
