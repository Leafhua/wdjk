package com.wdjk.webdemo624.service;

import com.wdjk.webdemo624.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表，保存用户相关信息 服务类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
public interface UserService extends IService<User> {

    /**
     * 注册用户
     *      -验证用户名，邮箱是否可用
     *      -加密密码
     *
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return
     */
    User register (String username,String password,String email);

    /**
     * 获取用户信息 Model Map
     *       - username 支持用户格式和邮箱格式
     *       - 若同时输入，优先考虑 username
     *
     * @param username 用户名
     * @param email 用户邮箱
     * @return Map userInfoMap 用户信息键值对
     */
    Map<String,Object> getUserInfoModelMap(String username,String email);

    /**
     * 获取用户信息 Model Map
     *
     * @param user 用户对象
     * @return Map 已过滤过的用户信息 Map
     */
    Map<String, Object> getUserInfoModelMap(User user);

    /**
     * 通过 id 获取用户信息
     *      -userID 须验证存在性
     * @param userId 用户id
     * @return User 用户对象
     */
    User getUserInfoById(int userId);

    /**
     * name 获取用户信息
     *       - username 需验证存在性
     *
     * @param username 用户名
     * @return User 对象
     */
    User getUserInfoByName(String username);

    /**
     * email 获取用户信息
     *      - email 需验证存在性
     *
     * @param email 用户邮箱
     * @return User 对象
     */
    User getUserInfoByEmail(String email);


    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 用户密码
     * @return User 通过登录验证,获取用户信息
     */
    User loginVerification(String username, String password);

    /**
     * 判断用户激活状态
     *
     * @param userCurrentState 用户当前激活状态
     * @return boolean 激活状态（true - 激活，false - 未激活）
     */
    boolean isUserActivatedByState(int userCurrentState);

    /**
     * 确认用户匹配 Cookie 用户
     *      - 比较输入用户名与 Cookie 用户名
     *      - 不匹配则抛出“无权限”异常，无权进行非本用户操作
     *
     * @param inputUsername 输入用户名
     * @param cookieUser Cookie 用户对象
     */
    void confirmUserMatchCookieUser(String inputUsername, User cookieUser);

    /**
     * 修改用户邮箱
     *      - username 需验证存在性
     *      - newEmail 需验证是否被占用
     *
     * @param username 用户名
     * @param newEmail 用户新邮箱
     */
    void alterUserEmail(String username, String newEmail);




}
