package com.wdjk.webdemo624.controller.api;

import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.controller.annotation.AccountActivation;
import com.wdjk.webdemo624.controller.annotation.LoginAuthorization;
import com.wdjk.webdemo624.dto.ApiJsonDTO;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: webDemo
 * @description
 * 账户api
 *      + 注册
 *      + 登录
 *      + 注销
 *      + 获取用户信息
 *      + 修改邮箱
 *      + 修改密码
 *      + 激活账户
 *      + 激活 token 验证
 *      + 图片验证码
 *      + 校验验证码
 *      + 忘记密码
 *      + 修改用户基本信息
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

    /**
     * 修改邮箱
     *
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @LoginAuthorization
    @RequestMapping(value = "/update-email",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO updateUserEmail(@RequestBody Map<String,Object> requestBodyParamsMap){
        String username = (String) requestBodyParamsMap.get(ParamConst.USERNAME);
        String email = (String) requestBodyParamsMap.get(ParamConst.EMAIL);

        validationService.check(ParamConst.USERNAME,username)
                .check(ParamConst.EMAIL,email);

        User cookieUser = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());

        userService.confirmUserMatchCookieUser(username,cookieUser);

        userService.alterUserEmail(username,email);
        return new ApiJsonDTO().success();

    }

    /**
     * 修改密码
     *
     * @param requestBodyParamsMap request-body 内 JSON数据
     * @return ApiJsonDTO 页面 JSON 传输对象
     */
    @LoginAuthorization @AccountActivation
    @RequestMapping(value = "/update-passsword",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO updateUserPassword(@RequestBody Map<String,Object> requestBodyParamsMap){

        String username = (String) requestBodyParamsMap.get(ParamConst.USERNAME);
        String newPassword = (String) requestBodyParamsMap.get(ParamConst.PASSWORD);

        validationService.check(ParamConst.USERNAME,username)
                .check(ParamConst.PASSWORD,newPassword);

        User cookieUser = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());
        userService.confirmUserMatchCookieUser(username,cookieUser);

        userService.alterUserPasswordByName(username,newPassword);

        return new ApiJsonDTO().success();
    }

    /**
     * 激活账户
     *
     * @param requestBodyParamsMap request-body 内 JSON 数据 email
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/activate",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO activateAccount(@RequestBody Map<String,Object> requestBodyParamsMap){
        String email = (String) requestBodyParamsMap.get(ParamConst.EMAIL);

        validationService.check(ParamConst.EMAIL,email);

        userService.confirmUserActivatedByEmail(email);

        long remainAllowSendEmailInterval = cacheService.getEmailKeyExpireTime(email);

        if (remainAllowSendEmailInterval != SetConst.EXPIRE_TIME_REDIS_NO_EXIST_KEY){
            Map<String,Object> timerMap = new HashMap<>(SetConst.SIZE_ONE);
            timerMap.put(ParamConst.TIMER,remainAllowSendEmailInterval / SetConst.TIME_THOUSAND_MS);

            return new ApiJsonDTO().fail().message(ApiMessage.WAIT_TIMER).model(timerMap);

        }

        emailService.sendAccountActivateMail(email,secretService.generateValidateEmailToken(email));

        cacheService.saveUserEmailKey(email);

        return new ApiJsonDTO().success();
    }

    /**
     * 激活 token 验证
     *      - 验证 token
     *      - 数据库激活用户
     *      - 修改客户端cookie(重新保存用户信息)
     *
     * @param token 传入的token
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/validate",method = RequestMethod.GET)
    public ApiJsonDTO validateActivateToken(@RequestParam(value = "token",required = false)String token){

        validationService.check(ParamConst.TOKEN,token);

        User activatedUser = userService.alterUserActivateStateByEmailToken(token);

        httpService.saveAuthenticationCookie(secretService.generateUserInfoAuthentication(activatedUser));

        return new ApiJsonDTO().success();
    }

    /**
     * 图片验证码
     *      - 页面生成验证码图片，刷新重新生成
     *      - 当前 Session 储存验证码
     */
    @RequestMapping(value = "/captcha",method = RequestMethod.GET)
    public void generateCaptchaPicture(){

        httpService.setCaptchaImageTypeResponseHeader();

        String captchaText = captchaService.getCaptchaText();

        httpService.saveCaptchaText(captchaText);

        BufferedImage captchaImage = captchaService.getCaptchaImage(captchaText);

        httpService.outputCaptchaImage(captchaImage);
    }

    /**
     * 校验验证码
     *      - 比较用户输入验证，是否匹配 session 中存储的5位随机验证码
     *
     * @param captcha 用户输入验证码
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "validate-captcha",method = RequestMethod.GET)
    public ApiJsonDTO validateCaptcha(@RequestParam(value = "captcha",required = false)String captcha){

        validationService.check(ParamConst.CAPTCHA,captcha);

        captchaService.validate(captcha,httpService.getCaptchaText());
        return new ApiJsonDTO().success();
    }

    /**
     * 忘记密码
     *      - 验证邮箱存在性
     *      - 生成随机零时密码，修改邮箱用户密码 - > 临时密码
     *      - 发送临时密码到指定 email
     *
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "forget-password",method = RequestMethod.POST)
    public ApiJsonDTO forgetPassword(@RequestBody Map<String ,Object> requestBodyParamsMap){

        String email = (String) requestBodyParamsMap.get(ParamConst.EMAIL);

        validationService.check(ParamConst.EMAIL,email);

        String temporaryRandomPassword = randomService.generateSixDigitsRandomPassword();
        userService.alterUserPasswordByEmail(email,temporaryRandomPassword);

        emailService.sendResetTemporaryPasswordMail(email,temporaryRandomPassword);

        return new ApiJsonDTO().success();
    }

    /**
     * 修改用户基本信息
     *      - birthday,descripton 允许”“，但不能为null
     * @param requestBodyParamsMap request-body 内 JSON 数据
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @LoginAuthorization
    @AccountActivation
    @RequestMapping(value = "/update-profile",method = RequestMethod.POST,consumes = "application/json")
    public ApiJsonDTO updateUserInfo(@RequestBody Map<String,Object> requestBodyParamsMap){
        String newBirthday = (String) requestBodyParamsMap.get(ParamConst.BIRTHDAY);
        String newDescription = (String) requestBodyParamsMap.get(ParamConst.DESCRIPTION);

        validationService.checkParamsNotAllNull(newBirthday,newDescription);
        validationService.check(ParamConst.BIRTHDAY,newBirthday)
                .check(ParamConst.DESCRIPTION,newDescription);

        User user = secretService.getUserInfoByAuthentication(httpService.getAuthenticationCookieValue());

        return new ApiJsonDTO().success()
                .model(userService.alterUserProfile(user.getFuName(),newBirthday,newDescription));

    }


}
