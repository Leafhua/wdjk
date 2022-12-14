package com.wdjk.webdemo624.utils;



import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.exception.UtilClassException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 *      - 保存 Cookie
 *      - 删除 Cookie
 *      - 获取指定 Cookie
 *
 * @author Suvan
 */
public final class CookieUtil {

    private CookieUtil() { }

    /**
     * 保存 Cookie
     *      - 设置 Cookie key-value
     *      - 设置 Cookie 保存时间
     *      - 设置 Cookie 保存路径 “\”
     *      - 设置 Cookie 的 HttpOnly 属性
     *
     * @param response http 响应
     * @param cookieName Cookie 名字
     * @param cookieValue Cookie 值
     * @param maxAge 设置 Cookie 过期时间
     */
    public static void saveCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
            cookie.setMaxAge(maxAge);
            cookie.setPath("/");
            cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    /**
     * 删除 Cookie
     *      - 有效时间设为 0 (即表示删除)
     *
     * @param request http 请求
     * @param response http 响应
     * @param cookieName Cookie 名字
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                cookie.setMaxAge(SetConst.EXPIRE_TIME_ZERO_S);
                cookie.setPath("/");
                cookie.setHttpOnly(true);

                response.addCookie(cookie);
                break;
            }
        }
    }

    /**
     * 获取指定 Cookie
     *      - 根据 Cookie 名，获取 Cookie 值
     *
     * @param request http 请求
     * @param cookieName Cookie 名
     * @return String Cookie 值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            throw new UtilClassException(ApiMessage.UNKNOWN_ERROR).log(LogWarnEnum.UC9);
        }

        for (Cookie cookie: request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
