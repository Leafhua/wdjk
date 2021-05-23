package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.WebDemoApplication;
import com.wdjk.webdemo624.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void testRegister(){
        userService.register("linxi","123456","1007664276@test.com");
    }

}