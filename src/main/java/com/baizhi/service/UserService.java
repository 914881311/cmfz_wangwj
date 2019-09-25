package com.baizhi.service;

import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserService {
    //展示全部
    HashMap<String,Object> queryByPage(Integer page, Integer rows);
    //删除
    void deleteByUser(User user);
    //修改
    void updateByUser(User user);
    //状态
    HashMap<String,Object> updateStatus(User user);
    //展示全部
    ArrayList<User> showAll(HttpServletRequest request);
}
