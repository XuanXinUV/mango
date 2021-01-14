package com.example.mapper;

import com.example.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUser(String id);

    Integer insertUser(User user);

    void updateUser(User user);

    void deleteUser(String id);
}