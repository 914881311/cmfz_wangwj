package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page, Integer rows){
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }
    @RequestMapping("/edit")//编辑
    public String edit(User user, String oper){//操作
        String id=null;
        //执行添加操作
        if(oper.equals("add")){

        }
        //执行修改操作
        if(oper.equals("edit")){
            userService.updateByUser(user);
        }
        //执行删除操作
        if(oper.equals("del")){
            userService.deleteByUser(user);
        }
        return id;
    }
    @RequestMapping("bannerUpload")
    public void bannerUpload(MultipartFile img_path, String id, HttpServletRequest request){
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        //获取文件夹
        File file = new File(realPath);
        //判断文件夹是否存在
        if(!file.exists()){
            file.mkdirs(); //创建文件夹
        }

        //获取文件名
        String filename = img_path.getOriginalFilename();

        //给文件加一个时间戳
        String name=new Date().getTime()+"-"+filename;

        //文件上传
        try {
            img_path.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("queryAllUser")
    public ArrayList<User> queryAllUser(HttpServletRequest request){
        ArrayList<User> users = userService.showAll(request);
        return users;
    }
}
