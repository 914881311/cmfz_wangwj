package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/editor")
public class EditorContorller {
    @RequestMapping("uploadEditor")
    public HashMap<String,Object> uploadEditor(MultipartFile photo, HttpServletRequest request){
        System.out.println("图片上传");

        HashMap<String, Object> map = new HashMap<>();
        try{
            String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");
            File file = new File(realPath);
            if(!file.exists()){
                file.mkdirs();
            }
            String filename = photo.getOriginalFilename();
            String name = new Date().getTime()+"-"+filename;
            //文件上传
            photo.transferTo(new File(realPath,name));
            //获取 http
            String scheme = request.getScheme();
            //获取 localhost
            String serverName = request.getServerName();
            //获取8989
            int serverPort = request.getServerPort();
            //获取项目名
            String contextPath = request.getContextPath();
            //拼接网络路径
            String url=scheme+"://"+serverName+":"+serverPort+contextPath+"/upload/editor"+name;
            map.put("error",0);
            map.put("url",url);
        }catch (Exception e){
            e.printStackTrace();
            map.put("error",0);
            map.put("message","上传失败");
        }
        return map;
    }
    @RequestMapping("/queryPhotos")
    public HashMap<String,Object> queryPhotos(HttpServletRequest request){
        HashMap<String, Object> maps = new HashMap<>();
        ArrayList<Object> lists = new ArrayList<>();
        //获取文件的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");

        //获取文件
        File file = new File(realPath);

        //获取文件夹中所有的   文件名
        String[] names = file.list();

        //遍历文件名
        for (int i = 0; i < names.length; i++) {
            System.out.println("+++++++++++++++++");
            //文件名
            String name = names[i];
            HashMap<String, Object> map = new HashMap<>();

            map.put("is_dir",false);  //是否是文件夹
            map.put("has_file",false);  //是否有文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());  //文件的大小
            map.put("is_photo",true);  //是否是图片
            String extension = FilenameUtils.getExtension(name);
            map.put("filetype",extension);  //图片的类型
            map.put("filename",name);  //图片的名字

            //字符串拆分
            String[] strs = name.split("-");
            String times =strs[0];
            long time = Long.parseLong(times);
            //指定一个日期格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateFormat.format(time);

            /*"2018-06-06 00:36:39"*/
            map.put("datetime",datetime);  //图片上传时间

            //将数据放入集合
            lists.add(map);
        }

        maps.put("current_url","http://localhost:8989/cmfz_zhangcn/upload/editor/");
        maps.put("total_count",lists.size());
        maps.put("file_list",lists);

        return maps;
    }
}
