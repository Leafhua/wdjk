package com.wdjk.webdemo624.service.impl;


import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.service.ISecretService;
import com.wdjk.webdemo624.utils.SecretUtil;
import com.wdjk.webdemo624.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * ISecretService 实现类
 *
 * @author Suvan
 */
@Service
public class SecretServiceImpl implements ISecretService {

    @Override
    public String generateValidateEmailToken(String email) {
        return SecretUtil.encodeBase64(email + "-" + StringUtil.getTodayTwentyFourClockTimestamp());
    }

    @Override
    public String generateUserInfoAuthentication(User user) {
        return SecretUtil.generateUserInfoToken(user);
    }

    @Override
    public User getUserInfoByAuthentication(String authentication) {
        //input authentication and key
        User user = SecretUtil.decryptUserInfoToken(authentication);
        if (user == null) {
            throw new ServiceException(ApiMessage.TOKEN_EXPIRED).log(LogWarnEnum.US4);
        }

        return user;
    }
}
