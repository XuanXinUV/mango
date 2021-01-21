package com.miyou.miyouapp.controller;

import com.miyou.miyouapp.io.createuser.CreateUserRequest;
import com.miyou.miyouapp.io.createuser.CreateUserResponse;
import com.miyou.miyouapp.io.getuser.GetUserRequest;
import com.miyou.miyouapp.io.getuser.GetUserResponse;
import com.miyou.miyouapp.io.updateuser.UpdateUserRequest;
import com.miyou.miyouapp.io.updateuser.UpdateUserResponse;
import com.miyou.miyouapp.service.UserService;
import com.miyou.miyouapp.utils.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public GetUserResponse getUser(@RequestHeader("Authorization") String auth, @RequestBody GetUserRequest request){
        return userService.getUser(request);
    }

    @RequestMapping("/create")
    @OperLog(operModel="user",operType="create",operDesc="createUser")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.createUser(request);
    }

    @RequestMapping("/update")
    @OperLog(operModel="user",operType="update",operDesc="updateUser")
    public UpdateUserResponse updateUser(@RequestBody UpdateUserRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userService.updateUser(request);
    }


}