package com.wdjk.webdemo624.controller.api;

import com.wdjk.webdemo624.utils.RandomUtil;
import com.wdjk.webdemo624.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-06-19 23:12
 **/
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value = "/stringtime",method = RequestMethod.GET)
    public String time(){
        return StringUtil.getTodayTwentyFourClockTimestamp();
    }
}
