package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Excel(name = "ID")
    private String id;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private Integer age;

    //注意：行高这个属性的单位是  设置值的2.5倍
    @Excel(name = "生日",format = "yyyy-MM-dd",width = 20,height = 20)
    private Date bir;

}
