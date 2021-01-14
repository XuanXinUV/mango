package com.example.io.getuser;

import lombok.*;

@Data
public class GetUserRequest {
    private String id;
    private String userName;
    private String realName;
    private String phone;
}
