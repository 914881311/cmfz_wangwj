package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    List<Album> queryAllByPage(@Param("start") Integer start, @Param("rows") Integer rows);
    Integer queryAllRecords();

    void save(Album album);

    void update(Album album);

    void delete(Album album);

}
