package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Excel(name="Id")
    private String id;

    @Excel(name="教师姓名",needMerge = true)
    private String name;

    @ExcelCollection(name="学生信息")
    private List<Student> students;

}
