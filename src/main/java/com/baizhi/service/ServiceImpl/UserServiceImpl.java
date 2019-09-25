package com.baizhi.service.ServiceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //records 总条数
        Integer records = userDao.queryRecords();
        map.put("records",records);

        //total 总页数
        Integer  total =records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //page 当前页
        map.put("page",page);

        //rows  每页展示条数
        List<User> banners = userDao.queryByPage((page-1)*rows, rows);
        map.put("rows",banners);

        return map;
    }

    @Override
    public void deleteByUser(User user) {
        userDao.delete(user);
    }

    @Override
    public void updateByUser(User user) {
        userDao.update(user);
    }

    @Override
    public HashMap<String, Object> updateStatus(User user) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            userDao.update(user);
            map.put("success","200");
            map.put("message","修改成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("success","400");
            map.put("message","修改失败");
        }
        return map;
    }

    @Override
    public ArrayList<User> showAll(HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/upload/photo");
        ArrayList<User> users = userDao.queryAll();
        ArrayList<User> list = new ArrayList<>();
        for (User user : users) {
            user.setAvatar(realPath+"//"+user.getAvatar());
            list.add(user);
        }
        try {
            //配置相关参数  参数：title:文档的标题,sheetName:工作簿的名字,要导出的实体类对象,要导出的集合
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户列表", "用户信息"),User.class,list);
            //导出Excel
            workbook.write(new FileOutputStream(new File("D://User.xls")));
            //释放资源
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }
}
