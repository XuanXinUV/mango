package com.miyou.miyouapp.io.getuser;

import lombok.Data;

@Data
public class GetUserRequest {
    private String id;
    private String userName;
    private String realName;
    private String phone;
}
