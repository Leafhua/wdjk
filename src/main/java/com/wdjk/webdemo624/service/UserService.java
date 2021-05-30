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




}
