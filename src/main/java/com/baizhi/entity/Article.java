package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {
    private String id;
    private String title;
    private String author;
    private String content;
    private Date crea_date;
    private String guru_id;
}
