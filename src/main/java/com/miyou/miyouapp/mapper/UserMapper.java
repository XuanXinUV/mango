package com.miyou.miyouapp.mapper;

import com.miyou.miyouapp.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUser(String id);

    Integer insertUser(User user);

    void updateUser(User user);

    void deleteUser(String id);

    User findUserByAccount(String userName);
}