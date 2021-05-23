package com.wdjk.webdemo624.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.dto.ApiJsonDTO;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import com.wdjk.webdemo624.utils.MailUtil;
import com.wdjk.webdemo624.utils.RandomUtil;
import com.wdjk.webdemo624.utils.SecretUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: webDemo
 * @description
 * 账户api
 *      + 注册
 *      + 登录
 *
 * @author: zhuhua
 * @create: 2021-05-05 16:37
 **/
@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Resource
    private  UserService userService;

    @Resource
    private MailUtil mailUtil;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${wdjk.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 登录
     *
     *
     * @param username  用户名
     * @param password  密码
     * @return
     */

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(@ApiParam(name = "name") @RequestParam("name") String username,
                        @ApiParam("密码") String password){

        if(!StringUtils.isNullOrEmpty(username)&&"123456".equals(password)){
        return "OK";}
        else{
            return "登录失败";
        }
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String login(String username,
                        String password,
                        String code,
                        String rememberMe,
                        HttpSession session,
                        HttpServletResponse response){

        return null;
    }

    /**
     * 注册
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON传输对象
     */
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public ApiJsonDTO registerAccount(@RequestBody Map<String,Object> requestBodyParamsMap){

        String username = (String) requestBodyParamsMap.get(ParamConst.USERNAME);
        String password = (String) requestBodyParamsMap.get(ParamConst.PASSWORD);
        String email = (String) requestBodyParamsMap.get(ParamConst.EMAIL);
        String code = RandomUtil.generateRandomString(6);
        User user = userService.register(username,password,email);



    }
}
