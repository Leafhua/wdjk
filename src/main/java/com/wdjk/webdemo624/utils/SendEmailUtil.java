package com.wdjk.webdemo624.utils;


import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.exception.UtilClassException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 发送邮件工具类
 *      - 发送（邮件）
 *
 * @author Suvan
 */
public final class SendEmailUtil {

    private SendEmailUtil() { }

    /**
     * 腾讯企业邮箱
     *      - 帐户名
     *      - 第三方授权码
     */
    private static final String FROM_USERNAME="leafzhuhua@163.com";
    private static final String FROM_AUTHORIZATION_CODE= "VJJCBSDRQOOTOIEW";

    /*
     * ***********************************************
     * 静态代码块
      *     - 读取 src/main/resources/neubbs.properties
     * ***********************************************
     */


    /**
     * 发送（邮件）
     *      - 构建邮件请求
     *      - 构建参数
     *      - 构建连接
     *      - 构建消息
     *          - 设置发件人 + 昵称
     *          - 设置主题 + 内容
     *          - 设置收件人
     *          - 保存更改
     *      - 发送邮件
     *
     * @param sendNickname 发件人昵称
     * @param receiveEmail 接收邮箱
     * @param sendSubject 发送主题
     * @param sendEmailContent 发送内容
     */
   public static void  send(String sendNickname, String receiveEmail,
                            String sendSubject, String sendEmailContent) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setUsername(FROM_USERNAME);
            sender.setPassword(FROM_AUTHORIZATION_CODE);
            sender.setHost(SetConst.TO_HOST);
            sender.setProtocol(SetConst.TO_SMTPS);
            sender.setPort(Integer.parseInt(SetConst.TO_SMTP_SSL_PROT));

        Properties properties = new Properties();
            properties.setProperty(SetConst.TO_AUTH, SetConst.TO_AUTH_TRUE);
            properties.setProperty(
                    SetConst.TO_MAIL_SMTP_SOCKETFACTORY_CLASS,
                    SetConst.TO_JAVAX_NET_SSL_SSLSOCKETFACTORY
            );
            properties.setProperty(SetConst.TO_MAIL_SMTP_SOCKETFACTORY_PORT, SetConst.TO_SMTP_SSL_PROT);

        sender.setJavaMailProperties(properties);

        Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_USERNAME, FROM_AUTHORIZATION_CODE);
            }
        });

        MimeMessage message = new MimeMessage(mailSession);
            try {
                message.setFrom(new InternetAddress(FROM_USERNAME, sendNickname));

                message.setSubject(sendSubject, SetConst.FROM_SUBJECT_ENCODING);
                message.setContent(sendEmailContent, SetConst.FROM_CONTENT_TYPE);

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveEmail));

                message.saveChanges();

            } catch (UnsupportedEncodingException | MessagingException uee) {
                uee.printStackTrace();
            }

        sender.send(message);
    }
}
