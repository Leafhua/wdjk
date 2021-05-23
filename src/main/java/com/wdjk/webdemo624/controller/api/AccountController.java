package com.wdjk.webdemo624.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     *
     * @param user user中至少
     * @return
     */
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public Map<String,Object> register(User user,String vCode){



        Map<String,Object> map = new HashMap<>(2);
        //判空操作
        if (user == null){
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isNullOrEmpty(user.getFuName())){
            map.put("usernameMsg","账号不能为空！");
            return map;
        }
        if (StringUtils.isNullOrEmpty(user.getFuPassword())){
            map.put("passwordMsg","密码不能为空！");
            return map;
        }
        if (StringUtils.isNullOrEmpty(user.getFuEmail())){
            map.put("emailMsg","邮箱不能为空！");
            return map;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //判断数据库中是否存在

        wrapper.eq("fu_name",user.getFuName());
        User one = userService.getOne(wrapper);
        if (one != null){
            map.put("usernameMsg","用户名已被注册！");
        }
        wrapper.clear();

        wrapper.eq("fu_email",user.getFuEmail());
        one = userService.getOne(wrapper);
        if (one != null){
            map.put("emailMsg","该邮箱已被注册！");
        }
        wrapper.clear();

        String code = RandomUtil.generateRandomString(6);

        user.setFuPassword(SecretUtil.encryptMd5(user.getFuPassword()));
        user.setFuState(0);
        user.setFuAvator(domain+"/"+contextPath+ ParamConst.AVATAR_PATH+RandomUtil.generateRandomNumbers(1,1000)+".png");
        stringRedisTemplate.opsForValue().set(user.getFuEmail(),code,SetConst.TIME_SIXTY_S*30, TimeUnit.SECONDS);
        try {
            Context context = new Context();
            context.setVariable("email",user.getFuEmail());
            String url = domain + contextPath;
            context.setVariable("url",url);
            context.setVariable("code",code);
            String content = templateEngine.process("/mail/activation",context);
            mailUtil.sendMail (user.getFuEmail(),"激活账号",content);
            map.put("msg","验证码发送成功，30分钟内有效，请注意查收！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","无法发送邮件，请稍后重试！");
        }
        userMapper.insert(user);
        if(vCode == stringRedisTemplate.opsForValue().get(user.getFuEmail())){
            user.setFuState(SetConst.ACTIVE);
            userMapper.updateById(user);
        }
        else {
            map.put("codeMsg","验证码错误");
        }


        return map;
    }
}
