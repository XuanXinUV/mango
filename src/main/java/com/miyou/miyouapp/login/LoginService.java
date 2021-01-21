package com.miyou.miyouapp.login;

import com.miyou.miyouapp.entity.User;
import com.miyou.miyouapp.login.io.LoginRequest;
import com.miyou.miyouapp.login.io.LoginResponse;
import com.miyou.miyouapp.mapper.UserMapper;
import com.miyou.miyouapp.utils.MDUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    private static final String pwdIndex = "miyou";

    private static final String companycode = "miyou";

    @Value("${pwdsecret}")
    private String pwdsecret;

    @Value("${tokensecret}")
    private String tokensecret;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public LoginResponse login(LoginRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(request.getPassWord()) || StringUtils.isBlank(request.getUserName())) {
            return null;
        }
        String userName = request.getUserName();
        User user = userMapper.findUserByAccount(userName);
        String pwd = request.getPassWord();
        String tmptString = MDUtils.MD5EncodeForHex(pwdIndex + pwdsecret + pwd, "UTF-8")
                .toUpperCase();
        String token = null;
        LoginResponse response = new LoginResponse();
        if (tmptString.equals(user.getPassWord())) {
            token = MDUtils.MD5EncodeForHex(pwdIndex + tokensecret + pwd, "UTF-8")
                    .toUpperCase();
        }

        if(StringUtils.isNotBlank(token)){
            String rediskey = companycode + "_" + userName;
            redisTemplate.opsForValue().set(rediskey,token, TimeUnit.HOURS.toSeconds(1));
        }

        response.setToken(token);
        return response;
    }
}
