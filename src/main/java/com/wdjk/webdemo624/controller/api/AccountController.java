package com.wdjk.webdemo624.controller.api;

import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.controller.annotation.LoginAuthorization;
import com.wdjk.webdemo624.dto.ApiJsonDTO;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

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

    private final UserService userService;
    private final IValidationService validationService;
    private final ISecretService secretService;
    private final ICacheService cacheService;
    private final IFtpService ftpService;
    private final IHttpService httpService;
    private final IEmailService emailService;
    private final ICaptchaService captchaService;
    private final IRandomService randomService;

    @Autowired
    private AccountController(UserService userService, IValidationService validationService, ISecretService secretService, ICacheService cacheService, IFtpService ftpService, IHttpService httpService, IEmailService emailService, ICaptchaService captchaService, IRandomService randomService) {
        this.userService = userService;
        this.validationService = validationService;
        this.secretService = secretService;
        this.cacheService = cacheService;
        this.ftpService = ftpService;
        this.httpService = httpService;
        this.emailService = emailService;
        this.captchaService = captchaService;
        this.randomService = randomService;
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
        User user = userService.register(username,password,email);
        return new ApiJsonDTO().success().model(userService.getUserInfoModelMap(user));
    }


    /**
     * 登录
     *      - username（支持用户名 or 邮箱格式）
     *
     * @param requestBodyParamsMap  request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ApiJsonDTO loginAccount(@RequestBody Map<String,Object> requestBodyParamsMap){
        String username = (String) requestBodyParamsMap.get(ParamConst.USERNAME);
        String password = (String) requestBodyParamsMap.get(ParamConst.PASSWORD);

        validationService.checkUsername(username)
                .check(ParamConst.PASSWORD,password);

        User user = userService.loginVerification(username,password);

        String authentication = secretService.generateUserInfoAuthentication(user);
        httpService.saveAuthenticationCookie(authentication);


        Map<String,Object> modelMap = new LinkedHashMap<>(SetConst.LENGTH_TWO);
        modelMap.put(ParamConst.AUTHENTICATION,authentication);
        modelMap.put(ParamConst.STATE,userService.isUserActivatedByState(user.getFuState()));

        return new ApiJsonDTO().success().model(modelMap);
    }


    /**
     * 注销
     *
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @LoginAuthorization
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ApiJsonDTO logoutAccount(){
        httpService.removeCookie(ParamConst.AUTHENTICATION);

        return new ApiJsonDTO().success();
    }

    /**
     * 获取用户信息
     *
     *
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @LoginAuthorization
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ApiJsonDTO userInfo(){
        User user =  secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());
        return new ApiJsonDTO().success().model(user);
    }

    @LoginAuthorization
    @RequestMapping(value = "/update-email",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO updateUserEmail(@RequestBody Map<String,Object> requestBodyMap){
        String username = (String) requestBodyMap.get(ParamConst.USERNAME);
        String email = (String) requestBodyMap.get(ParamConst.EMAIL);

        validationService.check(ParamConst.USERNAME,username)
                .check(ParamConst.EMAIL,email);

        User cookieUser = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());

        userService.confirmUserMatchCookieUser(username,cookieUser);

        userService.alterUserEmail(username,email);
        return new ApiJsonDTO().success();

    }


}
