package com.wdjk.webdemo624.service.impl;

import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表，保存用户相关信息 服务实现类
 * </p>
 *
 * @author zhuhua
 * @since 2021-05-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
