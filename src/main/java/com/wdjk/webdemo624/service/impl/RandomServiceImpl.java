package com.wdjk.webdemo624.service.impl;


import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.service.IRandomService;
import com.wdjk.webdemo624.utils.RandomUtil;
import org.springframework.stereotype.Service;

/**
 * IRandomService 实现类
 *
 * @author Suvan
 */
@Service("randomServiceImpl")
public class RandomServiceImpl implements IRandomService {

    @Override
    public String generateSixDigitsRandomPassword() {
        return RandomUtil.generateRandomString(SetConst.TEMPORARY_PASSWORD_LENGTH);
    }
}
