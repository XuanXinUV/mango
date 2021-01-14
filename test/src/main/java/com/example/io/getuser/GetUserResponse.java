package com.example.io.getuser;

import lombok.*;

@Data
public class GetUserResponse {
    private String id;
    private String userName;
    private String passWord;
    private String realName;
}
