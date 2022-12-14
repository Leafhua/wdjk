package com.wdjk.webdemo624.service;


import com.wdjk.webdemo624.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * FTP 服务接口
 *      - 创建用户个人目录
 *      - 上传用户头像
 *      - 生成服务器头像文件名
 *
 * @author Suvan
 */
public interface IFtpService {

    /**
     * 创建用户个人目录
     *      - ftp 服务器上私人目录
     *      - 同时创建头像目录
     *
     * @param user 注册的新用户对象
     */
    void createUserPersonalDirectory(User user);

    /**
     * 上传用户头像
     *
     * @param user 用户对象
     * @param avatarFile 头像文件对象
     */
    void uploadUserAvatar(User user, MultipartFile avatarFile);

    /**
     * 生成服务器头像文件名
     *
     * @param avatarFile 头像文件对象
     * @return String 服务器头像文件名
     */
    String generateServerAvatarFileName(MultipartFile avatarFile);
}
