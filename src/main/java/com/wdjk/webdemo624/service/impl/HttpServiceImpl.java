package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.Ipaddress;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.mapper.IpaddressMapper;
import com.wdjk.webdemo624.service.IHttpService;

import com.wdjk.webdemo624.service.IpaddressService;
import com.wdjk.webdemo624.utils.CookieUtil;
import com.wdjk.webdemo624.utils.PublicParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IHttpService 实现类
 *      - request, response 运用 ThreadLocal 隐藏（ApiFilter 存入，PublicParamsUtil 去出）
 *
 * @author Suvan
 */
@Service
public class HttpServiceImpl implements IHttpService {



    private final IpaddressService ipaddressService;
    private final IpaddressMapper ipaddressMapper;

    @Autowired
    public HttpServiceImpl(IpaddressService ipaddressService, IpaddressMapper ipaddressMapper) {
        this.ipaddressService = ipaddressService;
        this.ipaddressMapper = ipaddressMapper;
    }

    @Override
    public void setCaptchaImageTypeResponseHeader() {
        HttpServletResponse response = PublicParamsUtil.getResponse();
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
    }

    @Override
    public void outputCaptchaImage(BufferedImage captchaImage) {
        try {
            ServletOutputStream outputStream = PublicParamsUtil.getResponse().getOutputStream();

            ImageIO.write(captchaImage, "jpg", outputStream);

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new ServiceException(ApiMessage.GENERATE_CAPTCHA_FAIL).log(LogWarnEnum.US17);
        }
    }

    @Override
    public void saveCookie(String cookieName, String cookieValue) {
        //get cookie max day in neubbs.properties
        CookieUtil.saveCookie(PublicParamsUtil.getResponse(), cookieName,
                cookieValue, 15);
    }

    @Override
    public Ipaddress savaIPAddress(){
        Ipaddress ipaddress = new Ipaddress();
        HttpServletRequest request= PublicParamsUtil.getRequest();

        String ipAddress;
        try {
            ipAddress = PublicParamsUtil.getRequest().getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || SetConst.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || SetConst.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || SetConst.UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (SetConst.LOCALHOST.equals(ipAddress)) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(SetConst.SEPARATOR) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        ipaddress.setFiIpv4(ipAddress);
        if (ipaddressMapper.insert(ipaddress) == 0){
            throw new ServiceException(ApiMessage.DATABASE_EXCEPTION).log(LogWarnEnum.HS1);
        }
        return ipaddressService.getById(ipaddress.getFiId());
    }

    @Override
    public void removeCookie(String cookieName) {
        CookieUtil.removeCookie(PublicParamsUtil.getRequest(), PublicParamsUtil.getResponse(), cookieName);
    }

    @Override
    public String getCookieValue(String cookieName) {
        return CookieUtil.getCookieValue(PublicParamsUtil.getRequest(), cookieName);
    }

    @Override
    public void saveAuthenticationCookie(String authentication) {
       this.saveCookie(ParamConst.AUTHENTICATION, authentication);
    }

    @Override
    public String getAuthenticationCookieValue() {
        return CookieUtil.getCookieValue(PublicParamsUtil.getRequest(), ParamConst.AUTHENTICATION);
    }

    @Override
    public boolean isUserLoginState() {
        return this.getAuthenticationCookieValue() != null;
    }

    @Override
    public void saveCaptchaText(String captchaText) {
        PublicParamsUtil.getSession().setAttribute(SetConst.SESSION_CAPTCHA, captchaText);
    }

    @Override
    public String getCaptchaText() {
        return (String) PublicParamsUtil.getSession().getAttribute(SetConst.SESSION_CAPTCHA);
    }

    @Override
    public void destroySession() {
        PublicParamsUtil.getSession().invalidate();
    }

    @Override
    public void increaseOnlineLoginUserNumber() {
        PublicParamsUtil.getContext().setAttribute(ParamConst.LOGIN_USER, this.getOnlineLoginUserNumber() + 1);
    }

    @Override
    public void decreaseOnlineLoginUserNumber() {
        PublicParamsUtil.getContext().setAttribute(ParamConst.LOGIN_USER, this.getOnlineLoginUserNumber() - 1);
    }

    @Override
    public int getOnlineLoginUserNumber() {
        return (int) PublicParamsUtil.getContext().getAttribute(ParamConst.LOGIN_USER);
    }
}
