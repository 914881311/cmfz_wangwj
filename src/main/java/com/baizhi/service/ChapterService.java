package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface ChapterService {
    //查询
    HashMap<String,Object> queryByPage(String albumId, Integer page, Integer rows);

    //添加
    String add(Chapter chapter);
    //删除
    void del(Chapter chapter);

    //文件上传  修改数据
    HashMap<String,Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request);
    //文件下载
    void downloadChapter(String fileName, HttpServletRequest request, HttpServletResponse response);
}
