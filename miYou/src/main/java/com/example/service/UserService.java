package com.example.service;

import com.example.entity.User;
import com.example.io.createuser.CreateUserRequest;
import com.example.io.createuser.CreateUserResponse;
import com.example.io.getuser.GetUserRequest;
import com.example.io.getuser.GetUserResponse;
import com.example.io.updateuser.UpdateUserRequest;
import com.example.io.updateuser.UpdateUserResponse;
import com.example.mapper.UserMapper;
import com.example.utils.RegExpValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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
        response.setCreateTime(user.getCreateTime());
        response.setUpdateTime(user.getUpdateTime());
        response.setBeId(user.getBeId());
        response.setPhone(user.getPhone());
        logger.info("get user end!");
        return response;
    }

    public CreateUserResponse createUser(CreateUserRequest request){
        logger.info("create user start!");
        CreateUserResponse response = new CreateUserResponse();
        if(!RegExpValidator.IsTelephone(request.getPhone())){
            return response;
        }
        String uuid = UUID.randomUUID().toString();
        Date date = new Date();
        User user = User.builder()
                .id(uuid)
                .passWord(request.getPassWord())
                .realName(request.getRealName())
                .userName(request.getUserName())
                .createTime(date)
                .updateTime(date)
                .beId(1)
                .phone(request.getPhone())
                .build();
        userMapper.insertUser(user);
        response.setId(uuid);
        logger.info("create user end!");
        return response;
    }

    public UpdateUserResponse updateUser(UpdateUserRequest request){
        logger.info("update user start!");
        UpdateUserResponse response = new UpdateUserResponse();
        if(!RegExpValidator.IsTelephone(request.getPhone())){
            response.setResult(false);
            return response;
        }
        User user = User.builder()
                .id(request.getId())
                .passWord(request.getPassWord())
                .realName(request.getRealName())
                .userName(request.getUserName())
                .updateTime(new Date())
                .phone(request.getPhone())
                .build();
        userMapper.updateUser(user);
        response.setResult(true);
        logger.info("update user end!");
        return response;
    }
}