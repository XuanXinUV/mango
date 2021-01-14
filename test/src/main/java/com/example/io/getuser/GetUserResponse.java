package com.example.io.getuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
public class GetUserResponse {
    private String id;
    private String userName;
    private String passWord;
    private String realName;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private Integer beId;
    private String phone;
}
