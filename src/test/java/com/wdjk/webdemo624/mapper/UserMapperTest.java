package com.wdjk.webdemo624.mapper;

import com.wdjk.webdemo624.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(1);
        System.out.println(user);

        user = userMapper.selectById(3);
        System.out.println(user);
    }
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setFuName("瓜皮22号");
        user.setFuPassword("5232");
        user.setFuEmail("51242323@qk.com");
        user.setFuBirthday("1999.03.03");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user.getFuId());
    }


}