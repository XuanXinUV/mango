package com.example.mapper;

import com.example.entity.User;
import com.example.io.createuser.CreateUserRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUser(String id);

    Integer insertUser(User user);
}