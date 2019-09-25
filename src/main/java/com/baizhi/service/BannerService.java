package com.baizhi.service;


import com.baizhi.entity.Banner;

import java.util.HashMap;

public interface BannerService {
    //展示全部
    HashMap<String,Object> queryByPage(Integer page,Integer rows);
    //添加
    String add(Banner banner);
    //修改
    void updateByBanner(Banner banner);
    //删除
    void deleteByBanner(Banner banner);
    //状态
    HashMap<String,Object> updateStatus(Banner banner);
}
