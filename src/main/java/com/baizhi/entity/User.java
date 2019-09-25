package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name="Id")
    private String id;
    @Excel(name="头像",type = 2 ,width = 40 , height = 20,imageType = 1)
    private String avatar;
    @Excel(name="名字")
    private String name;
    @ExcelIgnore
    private String Law_name;
    @Excel(name="密码")
    private String password;
    @Excel(name="性别")
    private String sex;
    @Excel(name="状态")
    private String status;
    @Excel(name="手机号")
    private String phone;
    @Excel(name="注册时间")
    private String crea_date;
    @Excel(name="所在地")
    private String city; //所在地
    @Excel(name="签名")
    private String sign;//签名
}
