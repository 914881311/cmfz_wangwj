package com.baizhi.controller;

import com.baizhi.entity.City;
import com.baizhi.entity.Pro;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("echarts")
public class EchartsController {

    @RequestMapping("queryUserCount")
    public HashMap<String,Object> queryUserCount(){

        HashMap<String, Object> map = new HashMap<>();

        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        map.put("boys", Arrays.asList(500, 200, 800, 900, 100, 200));
        map.put("girls", Arrays.asList(500, 200, 200, 100, 100, 200));

        return map;
    }

    @RequestMapping("queryUserMap")
    public ArrayList<Pro> queryUserMap(){

        ArrayList<Pro> pros = new ArrayList<>();

        ArrayList<City> boys = new ArrayList<>();
        boys.add(new City("北京","200"));
        boys.add(new City("深圳","800"));
        boys.add(new City("黑龙江","200"));
        boys.add(new City("西藏","600"));

        ArrayList<City> girls = new ArrayList<>();
        girls.add(new City("四川","200"));
        girls.add(new City("重庆","700"));
        girls.add(new City("山东","222"));
        girls.add(new City("云南","100"));

        Pro pro1 = new Pro("小男生", boys);
        Pro pro2 = new Pro("小姑娘", girls);

        pros.add(pro1);
        pros.add(pro2);

        return pros;
    }
}
