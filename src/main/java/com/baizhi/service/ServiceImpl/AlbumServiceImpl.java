package com.baizhi.service.ServiceImpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
@Transactional//针对增删改
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Override
    public HashMap<String, Object> showAllPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //records 总条数
        Integer records = albumDao.queryAllRecords();
        map.put("records",records);
        //total 总页数
        Integer  total =records%rows==0?records/rows:records/rows+1;
        map.put("total",total);
        //page 当前页
        map.put("page",page);
        //rows  每页展示条数
        List<Album> banners = albumDao.queryAllByPage((page-1)*rows, rows);
        map.put("rows",banners);
        return map;
    }

    @Override
    public String add(Album album) {
        String uuid = UUIDUtil.getUUID();
        album.setId(uuid);
        album.setCrea_date(new Date());
        albumDao.save(album);
        System.out.println("---service banner数据入库==："+album);
        return uuid;
    }

    @Override
    public void updateByAlbum(Album album) {
        albumDao.update(album);
    }

    @Override
    public void deleteByAlbum(Album album) {
        albumDao.delete(album);
    }

}
