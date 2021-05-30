package com.wdjk.webdemo624.controller.handler;


import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.utils.SecretUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * 解密配置文件处理器
 *      - 继承 Spring 的 PropertyPlaceholderConfigurer, 读取配置文件（*.properties）时会调用
 *      - 加密内容解析（例如：密码）
 *
 * @author Suvan
 */
@Component
public class DecryptConfigurationFileHandler extends PropertyPlaceholderConfigurer {
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        //decrypt contain 'password' for property field
        if (propertyName.contains(ParamConst.PASSWORD)) {
            return SecretUtil.decodeBase64(propertyValue);
        }

        return super.convertProperty(propertyName, propertyValue);
    }
}
