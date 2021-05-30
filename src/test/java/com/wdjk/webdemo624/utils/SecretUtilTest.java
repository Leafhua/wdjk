package com.wdjk.webdemo624.utils;

import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class SecretUtilTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void JWTtest(){
        User user = userMapper.selectById(60);
        System.out.println(user);
        String token = SecretUtil.generateUserInfoToken(user);
        System.out.println(token);
        System.out.println(SecretUtil.decryptUserInfoToken(token));

    }
}