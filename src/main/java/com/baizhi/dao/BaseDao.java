package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDao<T> {
    List<T> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer queryRecords();

    void save(T t);

    void update(T t);

    void delete(Banner banner);
}
