package com.wdjk.webdemo624.controller.interceptor;


import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.controller.annotation.AccountActivation;
import com.wdjk.webdemo624.controller.annotation.AdminRank;
import com.wdjk.webdemo624.controller.annotation.LoginAuthorization;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.PermissionException;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.utils.CookieUtil;
import com.wdjk.webdemo624.utils.SecretUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  api 拦截器
 *      - 登录验证
 *      - 账户激活验证
 *      - 管理员验证
 *
 *  @author Suvan
 */

public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //judge @LoginAuthorization, @AccountActivation and @AdminRank
        doLoginAuthorization(request, handler);
        doAccountActivation(request, handler);
        doAdminRank(request, handler);

        //through the api interceptor
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView modelAndView) throws Exception { }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object obj, Exception exception) throws Exception { }

    /*
     * ***********************************************
     * private method
     * ***********************************************
     */

    /*
     * ***********************************************
     * authority management method
     * ***********************************************
     */

    /**
     * 执行登录验证
     *      - 判断 api 函数是否标识 @LoginAuthorization
     *      - 判断是否存在 authentication Cookie（不存在表明未登陆, 未登录无权操作）
     *      - 判断 authentication Cookie 是否解密成功（解密失败，表示认证信息已经过期）
     *
     * @param request http 请求
     * @param handler 接口方法对象
     */
    private void doLoginAuthorization(HttpServletRequest request, Object handler) throws ServiceException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(LoginAuthorization.class) != null) {
            String authentication =  CookieUtil.getCookieValue(request, ParamConst.AUTHENTICATION);
            this.judgeAuthentication(authentication);
        }
    }

    /**
     * 执行账户激活验证
     *      - 判断 api 函数是否标识 @AccountActivation
     *      - 判断是否存在 authentication Cookie（不存在表明未登陆, 未登录无权操作）
     *      - 判断 authentication Cookie 是否解密成功（解密失败，表示认认证信息已经过期）
     *      - 从认证信息内获取用户信息，判断用户激活状态
     *
     * @param request http 请求
     * @param handler 方法对象
     */
    private void doAccountActivation(HttpServletRequest request, Object handler) throws ServiceException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(AccountActivation.class) != null) {
            String authentication = CookieUtil.getCookieValue(request, ParamConst.AUTHENTICATION);
            User currentUser = this.judgeAuthentication(authentication);

            //judge user state
            if (currentUser.getFuState() == SetConst.ACCOUNT_NO_ACTIVATED_STATE) {
                throw new PermissionException(ApiMessage.NO_ACTIVATE).log(LogWarnEnum.US17);
            }
        }
    }

    /**
     * 执行管理员权限验证
     *      - 判断 api 函数是否标识 @AdminRank
     *      - 判断是否存在 authentication Cookie（不存在表明未登陆, 未登录无权操作）
     *      - 判断 authentication Cookie 是否解密成功（解密失败，表示认认证信息已经过期）
     *      - 从认证信息内获取用户信息，判断用户权限
     *
     * @param request http 请求
     * @param handler 方法对象
     */
    private void doAdminRank(HttpServletRequest request, Object handler) throws ServiceException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(AdminRank.class) != null) {
            String authentication = CookieUtil.getCookieValue(request, ParamConst.AUTHENTICATION);
            User currentUser = this.judgeAuthentication(authentication);

            //judge user rank
            if (!SetConst.RANK_ADMIN.equals(currentUser.getFuRank())) {
                throw new PermissionException(ApiMessage.NO_PERMISSION).log(LogWarnEnum.AT3);
            }
        }
    }

    /*
     * ***********************************************
     * judge method
     * ***********************************************
     */

    /**
     * 判断 authentication 认证信息函数
     *      - 若出错则抛出异常
     *
     * @param authentication Cookie 内认证信息
     * @return UserDO 用户信息对象实例
     */
    private User judgeAuthentication(String authentication) {
         //judge whether login
         if (authentication == null) {
             throw new PermissionException(ApiMessage.NO_PERMISSION).log(LogWarnEnum.AT2);
         }

        //judge token validity, throw token expired exception
        User user = SecretUtil.decryptUserInfoToken(authentication);
        if (user == null) {
            throw new PermissionException(ApiMessage.TOKEN_EXPIRED).log(LogWarnEnum.AT1);
        }

        return user;
    }
}
