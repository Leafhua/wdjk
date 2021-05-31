package com.wdjk.webdemo624.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.PermissionException;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdjk.webdemo624.utils.*;
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
    UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();

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

    @Override
    public User loginVerification(String username, String password) {
        User user;

        try {
            user = this.getUserInfoByName(username);
        }
        catch (ServiceException ae){
            throw new ServiceException(ApiMessage.USERNAME_OR_PASSWORD_INCORRECT).log(ae.getLog());
        }

        String cipherText = this.encryptUserPassword(password);
        if (!cipherText.equals(user.getFuPassword())){
            throw new ServiceException(ApiMessage.USERNAME_OR_PASSWORD_INCORRECT).log(LogWarnEnum.US7);
        }

        return user;
    }

    @Override
    public boolean isUserActivatedByState(int userCurrentState) {
        return userCurrentState == SetConst.ACCOUNT_ACTIVATED_STATE;
    }

    @Override
    public void confirmUserMatchCookieUser(String inputUsername, User cookieUser) {
        if (cookieUser == null || !inputUsername.equals(cookieUser.getFuName())) {
            throw new PermissionException(ApiMessage.NO_PERMISSION).log(LogWarnEnum.US10);
        }
    }

    @Override
    public void alterUserEmail(String username, String newEmail) {
        this.getUserInfoByName(username);
        this.confirmUserNotOccupiedByEmail(newEmail);
        updateWrapper.clear();
        updateWrapper.eq("fu_name",username).set("fu_email",newEmail);
        if (userMapper.update(null,updateWrapper) != 1){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.US2);
        }
    }

    @Override
    public void alterUserPasswordByName(String username, String newPassword) {
        this.getUserInfoByName(username);
        this.updateUserPasswordByName(username,newPassword);
    }

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param newPassword 新密码
     */
    private void updateUserPasswordByName(String username, String newPassword) {
        updateWrapper.clear();
        updateWrapper.eq("fu_name",username).set("fu_password",this.encryptUserPassword(newPassword));
        if (userMapper.update(null,updateWrapper)!=1){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.US2);
        }

    }

    @Override
    public void confirmUserActivatedByEmail(String email) {
        if(this.isUserActivatedByState(this.getUserInfoByEmail(email).getFuState())){
            throw new ServiceException(ApiMessage.ACCOUNT_ACTIVATED).log(LogWarnEnum.US5);
        }
    }

    @Override
    public User alterUserActivateStateByEmailToken(String emailToken) {

        String plainText = SecretUtil.decodeBase64(emailToken);
        String[] array = plainText.split("-");
        if (array.length != SetConst.LENGTH_TWO){
            throw new ServiceException(ApiMessage.INVALID_TOKEN).log(LogWarnEnum.US12);
        }

        String email = array[0];
        if (!PatternUtil.matchEmail(email)){
            throw new ServiceException(ApiMessage.INVALID_TOKEN).log(LogWarnEnum.US12);
        }

        String expireTime = array[1];
        if (StringUtil.isExpire(expireTime)){
            throw new ServiceException(ApiMessage.LINK_INVALID).log(LogWarnEnum.US4);
        }

        this.confirmUserActivatedByEmail(email);
        updateWrapper.clear();
        updateWrapper.eq("fu_email",email).set("fu_state",SetConst.ACTIVE);
        if (userMapper.update(null,updateWrapper)!= 1 ){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.US2);
        }

        return userMapper.selectOne(updateWrapper);

    }

    @Override
    public void alterUserPasswordByEmail(String email, String newPassword) {
        String username = this.getUserInfoByEmail(email).getFuName();
        this.updateUserPasswordByName(username,newPassword);
    }

    @Override
    public Map<String, Object> alterUserProfile(String username, String birthday, String description) {
        this.getUserInfoByName(username);

/*        User updateUser = new User();
        updateUser.setFuName(username);
        updateUser.setFuBirthday(birthday == null ? "" : birthday);
        updateUser.setFuDescription(description == null ? "" : description);*/

        updateWrapper.clear();
        updateWrapper.eq("fu_name",username);
        updateWrapper.set("fu_birthday",birthday == null ? "" : birthday);
        updateWrapper.set("fu_description",description == null ? "" : description);


        if (userMapper.update(null,updateWrapper) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.US2);
        }

        return this.getUserInfoModelMap(userMapper.selectOne(updateWrapper));
    }
}
