package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("showAllByPage")
    public HashMap<String,Object> showAllByPage(Integer page, Integer rows){
        HashMap<String, Object> map = albumService.showAllPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")//编辑
    public String edit(Album album, String oper){//操作
        System.out.println("------------------------------------");
        String id=null;
        //执行添加操作
        if(oper.equals("add")){
            id = albumService.add(album);
        }
        //执行修改操作
        if(oper.equals("edit")){
            albumService.updateByAlbum(album);
        }
        //执行删除操作
        if(oper.equals("del")){
            albumService.deleteByAlbum(album);
        }
        return id;
    }

    @RequestMapping("albumUpload")
    public void albumUpload(MultipartFile cover, String id, HttpServletRequest request){
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        //获取文件夹
        File file = new File(realPath);
        //判断文件夹是否存在
        if(!file.exists()){
            file.mkdirs(); //创建文件夹
        }

        //获取文件名
        String filename = cover.getOriginalFilename();

        //给文件加一个时间戳
        String name=new Date().getTime()+"-"+filename;

        //文件上传
        try {
            cover.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //执行修改  修改文件的路径
        Album album = new Album();
        album.setId(id);
        album.setCover(name);
        System.out.println("=====执行修改操作："+album);
        albumService.updateByAlbum(album);
    }
}
