package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {
    private int id;
    private String username;
    private String password;
}
