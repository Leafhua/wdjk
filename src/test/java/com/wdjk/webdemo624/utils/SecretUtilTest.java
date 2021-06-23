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
        //User user = userMapper.selectById(60);
        User user = new User();
        User user1= new User();
        user1.setFuId(90);
        user1.setFuName("test");
        user1.setFuRank(1);
        user1.setFuState(1);
        assertEquals(user,SecretUtil.decryptUserInfoToken(SecretUtil.generateUserInfoToken(user)));

    }
    @Test
    public void JWTBlackTest(){

    }
}