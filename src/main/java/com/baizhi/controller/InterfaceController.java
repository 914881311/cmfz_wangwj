package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class InterfaceController {

    @Resource
    private BannerService bannerService;

    @Resource
    private AlbumService albumService;

    @Resource
    private ArticleService articleService;

    @RequestMapping("first_page")
    public HashMap<String, Object> first_page(String uid,String type,String sub_type){

        System.out.println(uid);

        HashMap<String, Object> map = new HashMap<>();

        if(uid!=null){

            //首页展示
            if(type.equals("all")){

                //处理轮播图数据
                HashMap<String, Object> bannerss = bannerService.queryByPage(1, 5);
                List<Banner> banners = (List<Banner>) bannerss.get("rows");
                //封装轮播图数据
                map.put("banner",banners);

                //处理专辑数据
                HashMap<String, Object> albumss = albumService.showAllPage(1, 6);
                List<Album> albums = (List<Album>) albumss.get("rows");
                //封装专辑数据
                map.put("album",albums);

                //处理文章数据
                HashMap<String, Object> articless = articleService.queryByPage(1, 2);
                List<Article> articles = (List<Article>) articless.get("rows");
                //封装文章数据
                map.put("article",articles);
            }

            //专辑的数据
            if(type.equals("wen")){

                //处理专辑数据
                HashMap<String, Object> albumss = albumService.showAllPage(1, 20);
                List<Album> albums = (List<Album>) albumss.get("rows");
                //封装专辑数据
                map.put("album",albums);
            }

            if(type.equals("si")){
                //文章的数据
                if(sub_type!=null){
                    if(sub_type.equals("ssyj")){
                        //展示上师言教的文章 自己上师的文章
                        //处理文章数据
                        HashMap<String, Object> articless = articleService.queryByPage(1, 2);
                        List<Article> articles = (List<Article>) articless.get("rows");
                        //封装文章数据
                        map.put("article",articles);
                    }else{
                        //展示显密法要的文章 其他上师的文章
                        //处理文章数据
                        HashMap<String, Object> articless = articleService.queryByPage(1, 20);
                        List<Article> articles = (List<Article>) articless.get("rows");
                        //封装文章数据
                        map.put("article",articles);
                    }
                }
            }
        }
        return map;
    }
}
