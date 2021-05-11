package com.wdjk.webdemo624.controller;

import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-04 14:49
 **/
@Api("Controller控制")
@RestController
public class TestController {
    @ApiOperation("helloController控制")
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(@ApiParam(value = "用户") String name,@ApiParam("年龄") int age){

        return "hello"+name+age;
    }

    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    public User hello2(){

        return new User();
    }


}
