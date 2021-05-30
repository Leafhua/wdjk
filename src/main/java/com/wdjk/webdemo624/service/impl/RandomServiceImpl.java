package com.wdjk.webdemo624.service.impl;

import org.neusoft.neubbs.constant.api.SetConst;
import org.neusoft.neubbs.service.IRandomService;
import org.neusoft.neubbs.utils.RandomUtil;
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
