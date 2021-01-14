package com.example.service;

import com.example.entity.User;
import com.example.io.createuser.CreateUserRequest;
import com.example.io.createuser.CreateUserResponse;
import com.example.io.getuser.GetUserRequest;
import com.example.io.getuser.GetUserResponse;
import com.example.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserMapper userMapper;

    public GetUserResponse getUser(GetUserRequest request){
        logger.info("get user start!");
        GetUserResponse response = new GetUserResponse();
        User user = userMapper.getUser(request.getId());
        response.setId(user.getId());
        response.setPassWord(user.getPassWord());
        response.setUserName(user.getUserName());
        response.setRealName(user.getRealName());
        logger.info("get user end!");
        return response;
    }

    public CreateUserResponse createUser(CreateUserRequest request){
        logger.info("create user start!");
        CreateUserResponse response = new CreateUserResponse();
        User user = User.builder()
                .passWord(request.getPassWord())
                .realName(request.getRealName())
                .userName(request.getUserName())
                .build();
        response.setId(userMapper.insertUser(user));
        logger.info("create user end!");
        return response;
    }
}