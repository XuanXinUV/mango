package com.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String id;
    private String userName;
    private String passWord;
    private String realName;
}
