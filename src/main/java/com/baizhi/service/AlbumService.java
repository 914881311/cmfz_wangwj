package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.HashMap;

public interface AlbumService {
    //展示全部
    HashMap<String,Object> showAllPage(Integer page, Integer rows);
    //添加
    String add(Album album);
    //修改
    void updateByAlbum(Album album);
    //删除
    void deleteByAlbum(Album album);
}
