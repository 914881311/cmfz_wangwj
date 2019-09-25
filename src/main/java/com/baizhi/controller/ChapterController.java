package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("/queryByPage")
    public HashMap<String,Object> queryByPage(String albumId,Integer page,Integer rows){
        HashMap<String, Object> map = chapterService.queryByPage(albumId, page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String albumId, String oper){

        String id=null;
        if(oper.equals("add")){

            chapter.setAlbum_id(albumId);
            System.out.println("===chapter==="+chapter);
            id = chapterService.add(chapter);
        }

        if(oper.equals("edit")){

        }

        if(oper.equals("del")){
            chapterService.del(chapter);
        }
        return id;
    }
    @RequestMapping("uploadChapter")
    public HashMap<String,Object> uploadChapter(MultipartFile url, String id, HttpServletRequest request){
        HashMap<String, Object> map = chapterService.uploadChapter(url, id, request);
        return map;
    }

    @RequestMapping("downloadChapter")
    public void downloadChapter(String fileName,HttpServletRequest request,HttpServletResponse response){
        chapterService.downloadChapter(fileName, request, response);
    }
}
