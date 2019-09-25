package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Banner {
    private String id;
    private String name;
    private String img_path;//图片路径
    private String status;//状态
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date up_date;//上传时间
    private String description;//描述
}
