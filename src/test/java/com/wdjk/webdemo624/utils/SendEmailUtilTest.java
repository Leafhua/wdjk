package com.wdjk.webdemo624.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SendEmailUtilTest {

    @Test
    public void testMailSender(){
        SendEmailUtil.send("瓜皮瓜皮瓜皮鸹","1050157326@qq.com","哈哈哈","gogogogogogoogogogo");
    }
}