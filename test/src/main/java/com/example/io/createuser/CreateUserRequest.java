package com.example.io.createuser;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String userName;
    private String passWord;
    private String realName;
    private String phone;
}
