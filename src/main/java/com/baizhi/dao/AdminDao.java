package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    //登陆
    public Admin login(Admin admin);
    //根据用户名名和密码查询
    public Admin SelectOne(@Param("username") String username, @Param("password") String password);
}
