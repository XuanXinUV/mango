package com.miyou.miyouapp.io.updateuser;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String id;
    private String userName;
    private String passWord;
    private String realName;
    private String phone;
}
