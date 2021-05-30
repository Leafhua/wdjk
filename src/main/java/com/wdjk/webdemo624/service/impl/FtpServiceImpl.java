package com.wdjk.webdemo624.service.impl;


import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.service.IFtpService;
import com.wdjk.webdemo624.utils.FtpUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * IFtpService 实现类
 *
 * @author Suvan
 */
@Service("ftpServiceImpl")
public class FtpServiceImpl implements IFtpService {

    @Override
    public void createUserPersonalDirectory(User user) {
        try {
            String userAvatarImageFilePath = "/user/" + user.getFuId() + "-" + user.getFuName() + "/avator";
            FtpUtil.createDirectory(userAvatarImageFilePath);
        } catch (IOException ioe) {
            throw new ServiceException(ApiMessage.FTP_SERVICE_EXCEPTION).log(LogWarnEnum.FTPS1);
        }
    }

    @Override
    public void uploadUserAvatar(User user, MultipartFile avatarFile) {
        try {
            String serverUploadPath = "/user/" + user.getFuId() + "-" + user.getFuName() + "/avator/";
            String serverFileName = this.generateServerAvatarFileName(avatarFile);

            FtpUtil.uploadFile(serverUploadPath, serverFileName, avatarFile.getInputStream());
        } catch (IOException ioe) {
            throw new ServiceException(ApiMessage.FTP_SERVICE_EXCEPTION).log(LogWarnEnum.FTPS2);
        }
    }

    @Override
    public String generateServerAvatarFileName(MultipartFile avatarFile) {
        return System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
    }
}
