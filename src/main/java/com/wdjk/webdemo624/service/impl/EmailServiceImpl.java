package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.service.IEmailService;

import com.wdjk.webdemo624.utils.SendEmailUtil;
import com.wdjk.webdemo624.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * IEmailServiceImpl 实现类
 *
 * @author Suvan
 */
@Service
public class EmailServiceImpl implements IEmailService {

    private final ThreadPoolTaskExecutor taskExecutor;
    private final String AccountApiVaslidateUrl = "http://localhost:8088/account/validate?token=";

    @Autowired
    public EmailServiceImpl(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    @Override
    public void sendAccountActivateMail(String email, String emailToken) {
        String activateUrl = AccountApiVaslidateUrl;
        if (activateUrl == null) {
           throw new ServiceException(ApiMessage.UNKNOWN_ERROR).log(LogWarnEnum.ES1);
        }
        String emailContent = StringUtil.generateActivateMailHtmlContent(activateUrl + emailToken);

        this.send(SetConst.EMAIL_SENDER_NAME, email, SetConst.EMAIL_SUBJECT_ACTIVATE, emailContent);
    }

    @Override
    public void sendResetTemporaryPasswordMail(String email, String temporaryRandomPassword) {
        String emailContent = email + " 邮箱用户临时密码为：" + temporaryRandomPassword + " ，请登陆后尽快修改密码！";
        this.send(
                SetConst.EMAIL_SENDER_NAME, email,
                SetConst.EMAIL_SUBJECT_TEMPORARY_PASSWORD, emailContent
        );
    }

    /*
     * ***********************************************
     * private method
     * ***********************************************
     */

    /**
     * 发送邮件
     *      - 线程池另起线程发送邮件
     *
     * @param sendNickname 发送人昵称
     * @param receiveEmail 接收邮箱
     * @param sendSubject 发送主题
     * @param sendEmailContent 发送内容
     */
    public void send(String sendNickname, String receiveEmail, String sendSubject, String sendEmailContent) {
        taskExecutor.execute(
                () -> SendEmailUtil.send(sendNickname, receiveEmail, sendSubject, sendEmailContent)
        );
    }
}
