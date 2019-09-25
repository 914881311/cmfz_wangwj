package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    List<User> queryByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer queryRecords();

    //void save(User user);

    void update(User user);

    void delete(User user);
    //展示全部
    ArrayList<User> queryAll();
}
