package com.example.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    private String id;
    private String userName;
    private String passWord;
    private String realName;
    private Date createTime;
    private Date updateTime;
    private Integer beId;
    private String phone;
}
