package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzWangwjApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Test
    public void contextLoads() {
        Admin admin = adminDao.SelectOne("王文竞", "111111");
    }
}
