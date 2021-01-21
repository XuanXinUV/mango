package com.miyou.miyouapp.service;

import com.miyou.miyouapp.entity.User;
import com.miyou.miyouapp.io.createuser.CreateUserRequest;
import com.miyou.miyouapp.io.createuser.CreateUserResponse;
import com.miyou.miyouapp.io.getuser.GetUserRequest;
import com.miyou.miyouapp.io.getuser.GetUserResponse;
import com.miyou.miyouapp.io.updateuser.UpdateUserRequest;
import com.miyou.miyouapp.io.updateuser.UpdateUserResponse;
import com.miyou.miyouapp.mapper.UserMapper;
import com.miyou.miyouapp.utils.MDUtils;
import com.miyou.miyouapp.utils.RegExpValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    private static final String pwdIndex = "miyou";

    @Value("${pwdsecret}")
    private String pwdsecret;

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

    public CreateUserResponse createUser(CreateUserRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("create user start!");
        CreateUserResponse response = new CreateUserResponse();
        if(!RegExpValidator.IsTelephone(request.getPhone())){
            return response;
        }
        String uuid = UUID.randomUUID().toString();
        Date date = new Date();
        String passWord = MDUtils.MD5EncodeForHex(pwdIndex + pwdsecret + request.getPassWord(), "UTF-8")
                .toUpperCase();
        User user = User.builder()
                .id(uuid)
                .passWord(passWord)
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

    public UpdateUserResponse updateUser(UpdateUserRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        logger.info("update user start!");
        UpdateUserResponse response = new UpdateUserResponse();
        if(!RegExpValidator.IsTelephone(request.getPhone())){
            response.setResult(false);
            return response;
        }
        String passWord = MDUtils.MD5EncodeForHex(pwdIndex + pwdsecret + request.getPassWord(), "UTF-8")
                .toUpperCase();
        User user = User.builder()
                .id(request.getId())
                .passWord(passWord)
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