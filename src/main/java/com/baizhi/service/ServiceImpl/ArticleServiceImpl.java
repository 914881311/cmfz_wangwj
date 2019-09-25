package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //records 总条数
        Integer records = articleDao.queryRecords();
        map.put("records",records);

        //total 总页数
        Integer  total =records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //page 当前页
        map.put("page",page);

        //rows  每页展示条数
        List<Article> articles = articleDao.queryByPage((page - 1) * rows, rows);
        map.put("rows",articles);

        return map;
    }

    @Override
    public HashMap<String, Object> delete(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        articleDao.delete(article);
        return map;
    }


    @Override
    public HashMap<String, Object> add(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String uuid = UUIDUtil.getUUID();
            article.setId(uuid);
            article.setCrea_date(new Date());
            article.setGuru_id("1");

            System.out.println("=添加数据=article="+article);
            articleDao.save(article);

            map.put("success","200");
            map.put("message","添加成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("message","添加失败");
        }

        return map;
    }

    @Override
    public HashMap<String, Object> update(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            articleDao.update(article);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;
    }
}
