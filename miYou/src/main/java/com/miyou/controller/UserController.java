package com.miyou.controller;

import com.miyou.io.createuser.CreateUserRequest;
import com.miyou.io.createuser.CreateUserResponse;
import com.miyou.io.getuser.GetUserRequest;
import com.miyou.io.getuser.GetUserResponse;
import com.miyou.io.updateuser.UpdateUserRequest;
import com.miyou.io.updateuser.UpdateUserResponse;
import com.miyou.service.UserService;
import com.miyou.utils.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public GetUserResponse getUser(@RequestBody GetUserRequest request){
        return userService.getUser(request);
    }

    @RequestMapping("/create")
    @OperLog(operModel="user",operType="create",operDesc="createUser")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @RequestMapping("/update")
    @OperLog(operModel="user",operType="update",operDesc="updateUser")
    public UpdateUserResponse updateUser(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
    }
}