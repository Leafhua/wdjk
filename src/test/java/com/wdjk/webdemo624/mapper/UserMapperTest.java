package com.wdjk.webdemo624.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.service.TopicService;
import com.wdjk.webdemo624.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicService topicService;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(59);
        System.out.println(user);

        System.out.println(user.getFuId());
        System.out.println(user.getFuCreatetime());
        Map<String,Object> map = JsonUtil.toMapByObject(user);
        System.out.println(map);
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
        System.out.println(user.getFuCreatetime());

    }
    @Test
    public void testList(){
        System.out.println(topicService.count());
    }

    @Test
    public void testPage(){
        Page<User> userPage = new Page<>();
        userPage.setCurrent(3);

    }




}