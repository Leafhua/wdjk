package com.wdjk.webdemo624.service.impl;


import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.exception.ParamsErrorException;
import com.wdjk.webdemo624.service.IValidationService;
import com.wdjk.webdemo624.utils.ParamValidateUtil;
import com.wdjk.webdemo624.utils.PatternUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * IValidationService 实现类
 *
 * @author Suvan
 */
@Service
public class ValidationServiceImpl implements IValidationService {

    @Override
    public IValidationService check(String paramType, String paramValue) {
        ParamValidateUtil.check(paramType, paramValue);
        return this;
    }

    @Override
    public IValidationService checkUsername(String username) {
        if (username == null) {
            throw new ParamsErrorException(ApiMessage.PARAM_ERROR).log(LogWarnEnum.VS2);
        }

        String paramType = PatternUtil.matchEmail(username) ? ParamConst.EMAIL : ParamConst.USERNAME;
        ParamValidateUtil.check(paramType, username);
        return this;
    }

    @Override
    public void checkCommand(String command, String... commandArray) {
        for (String commandElement: commandArray) {
            if (commandElement.equals(command)) {
                return;
            }
        }

        throw new ParamsErrorException(ApiMessage.PARAM_ERROR).log(LogWarnEnum.VS2);
    }

    @Override
    public void checkOnlyNotNullParam(String... params) {
        int len = params.length;
        if (len == 0 || len % SetConst.LENGTH_TWO != 0) {
            throw new ParamsErrorException(ApiMessage.PARAM_ERROR).log(LogWarnEnum.US4);
        }

        for (int i = 0; i < len; i += SetConst.LENGTH_TWO) {
             String type = params[i];
             String value = params[i + 1];

            //if value != null, check param
            if (value != null) {
                this.check(type, value);
            }
        }
    }

    @Override
    public void checkParamsNotAllNull(String... params) {
        for (String param: params) {
            if (param != null) {
                return;
            }
        }

        throw new ParamsErrorException(ApiMessage.PARAM_ERROR).log(LogWarnEnum.VS1);
    }

    @Override
    public void checkUserUploadAvatarNorm(MultipartFile avatarFile) {
        //no empty
        if (avatarFile.isEmpty()) {
            throw new ParamsErrorException(ApiMessage.NO_CHOICE_PICTURE).log(LogWarnEnum.VS3);
        }

        //match file type(.xxx)
        if (!PatternUtil.matchUserAvatarType(avatarFile.getContentType())) {
            throw new ParamsErrorException(ApiMessage.PICTURE_FORMAT_WRONG).log(LogWarnEnum.VS4);
        }

        //no exceed 5MB
        if (avatarFile.getSize() >  SetConst.USER_AVATOR_MAX_SIZE_FIVE_MB) {
            throw new ParamsErrorException(ApiMessage.PICTURE_TOO_LARGE).log(LogWarnEnum.VS7);
        }
    }
}
