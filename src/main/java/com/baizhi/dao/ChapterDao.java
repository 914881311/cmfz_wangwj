package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    List<Chapter> queryByPage(@Param("albumId") String albumId, @Param("start") Integer start, @Param("rows") Integer rows);

    Integer queryRecords();

    void save(Chapter chapter);

    void update(Chapter chapter);

    void delete(Chapter chapter);
}
