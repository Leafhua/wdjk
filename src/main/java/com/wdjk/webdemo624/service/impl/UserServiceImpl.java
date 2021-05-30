package com.wdjk.webdemo624.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdjk.webdemo624.utils.JsonUtil;
import com.wdjk.webdemo624.utils.MapFilterUtil;
import com.wdjk.webdemo624.utils.SecretUtil;
import com.wdjk.webdemo624.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表，保存用户相关信息 服务实现类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



    @Resource
    private UserMapper userMapper;

    QueryWrapper<User> wrapper = new QueryWrapper<>();

    @Override
    public User register(String username, String password, String email) {
        this.confirmUserNotOccupiedByUsername(username);
        this.confirmUserNotOccupiedByEmail(email);

        User user = new User();
        user.setFuName(username);
        user.setFuEmail(email);
        user.setFuPassword(this.encryptUserPassword(password));
        user.setFuAvator(StringUtil.generateUserAvatarUrl());


        if (userMapper.insert(user) != 1){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.US1);

        }

        return this.getUserInfoById(user.getFuId());
    }

    @Override
    public Map<String, Object> getUserInfoModelMap(String username, String email) {
        return null;
    }

    @Override
    public User getUserInfoById(int userId) {
        User user = userMapper.selectById(userId);
        if (user == null ){
            throw new ServiceException(ApiMessage.NO_USER).log(LogWarnEnum.US16);
        }
        return user;
    }

    @Override
    public User getUserInfoByName(String username) {
        wrapper.eq("fu_name",username);
        User user = userMapper.selectOne(wrapper);
        wrapper.clear();
        if (user == null ){
            throw new ServiceException(ApiMessage.NO_USER).log(LogWarnEnum.US16);
        }
        return user;
    }

    @Override
    public User getUserInfoByEmail(String email) {
        wrapper.eq("fu_email",email);
        User user = userMapper.selectOne(wrapper);
        wrapper.clear();
        if (user == null ){
            throw new ServiceException(ApiMessage.NO_USER).log(LogWarnEnum.US16);
        }
        return user;
    }


    /*
     * ***********************************************
     * confirm method
     * ***********************************************
     */

    /**
     * （用户名）确认用户未被占用
     *
     * @param username 用户名
     */
    private void confirmUserNotOccupiedByUsername(String username) {


        wrapper.eq("fu_name",username);
        if (userMapper.selectOne(wrapper) != null) {
            throw new ServiceException(ApiMessage.USERNAME_REGISTERED).log(LogWarnEnum.US11);
        }
        wrapper.clear();
    }

    /**
     * （邮箱）确认用户未被占用
     *
     * @param email 用户邮箱
     */
    private void confirmUserNotOccupiedByEmail(String email) {
        wrapper.eq("fu_email",email);
        if (userMapper.selectOne(wrapper)!= null) {
            throw new ServiceException(ApiMessage.EMAIL_REGISTERED).log(LogWarnEnum.US6);
        }
        wrapper.clear();
    }

    /*
     * ***********************************************
     * encrypt method
     * ***********************************************
     */

    /**
     * 加密用户密码
     *
     * @param password 用户密码
     * @return String MD5密文
     */
    private String encryptUserPassword(String password) {
        return SecretUtil.encryptMd5(SecretUtil.encryptMd5(password) + password);
    }

    @Override
    public Map<String, Object> getUserInfoModelMap(User user) {
        if (user == null) {
            return new HashMap<>(0);
        }

        Map<String, Object> userInfoMap = JsonUtil.toMapByObject(user);
        MapFilterUtil.filterUserInfo(userInfoMap);

        return userInfoMap;
    }
}
