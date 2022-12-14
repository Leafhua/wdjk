package com.wdjk.webdemo624.service.impl;



import com.google.code.kaptcha.Producer;
import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * ICaptchaService 实现类
 *
 * @author Suvan
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    private final Producer captchaProducer;

    @Autowired
    public CaptchaServiceImpl(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Override
    public String getCaptchaText() {
        return captchaProducer.createText();
    }

    @Override
    public BufferedImage getCaptchaImage(String captchaText) {
        return captchaProducer.createImage(captchaText);
    }

    @Override
    public void validate(String inputCaptcha, String sessionCaptcha) {
        //no generate captcha, session not exist captcha text
        if (sessionCaptcha == null) {
            throw new ServiceException(ApiMessage.NO_GENERATE_CAPTCHA).log(LogWarnEnum.US8);
        }

        //input captcha not match session captcha(user input error)
        if (!inputCaptcha.equals(sessionCaptcha)) {
            throw new ServiceException(ApiMessage.CAPTCHA_INCORRECT).log(LogWarnEnum.US9);
        }
    }
}
