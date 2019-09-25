package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    List<Article> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer queryRecords();

    void delete(Article article);
    void save(Article article);
    void update(Article article);
}
