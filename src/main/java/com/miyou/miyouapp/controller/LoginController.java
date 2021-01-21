package com.miyou.miyouapp.controller;

import com.miyou.miyouapp.login.LoginService;
import com.miyou.miyouapp.login.io.LoginRequest;
import com.miyou.miyouapp.login.io.LoginResponse;
import com.miyou.miyouapp.utils.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    @OperLog(operModel="login",operType="login",operDesc="login")
    public LoginResponse login(@RequestBody LoginRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return loginService.login(request);
    }
}
