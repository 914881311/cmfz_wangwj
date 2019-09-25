package com.baizhi.service.ServiceImpl;


import com.baizhi.dao.BaseDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional//针对增删改
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BaseDao baseDao;
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        //records 总条数
        Integer records = baseDao.queryRecords();
        map.put("records",records);

        //total 总页数
        Integer  total =records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //page 当前页
        map.put("page",page);

        //rows  每页展示条数
        List<Banner> banners = baseDao.queryByPage((page-1)*rows, rows);
        map.put("rows",banners);

        return map;
    }

    @Override
    public String add(Banner banner) {
        String uuid = UUIDUtil.getUUID();
        banner.setId(uuid);
        banner.setStatus("1");
        banner.setUp_date(new Date());
        baseDao.save(banner);
        System.out.println("---service banner数据入库==："+banner);
        return uuid;
    }
    //修改
    @Override
    public void updateByBanner(Banner banner) {
        baseDao.update(banner);
    }

    @Override
    public void deleteByBanner(Banner banner) {
        baseDao.delete(banner);
    }

    @Override
    public HashMap<String, Object> updateStatus(Banner banner) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            baseDao.update(banner);
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
