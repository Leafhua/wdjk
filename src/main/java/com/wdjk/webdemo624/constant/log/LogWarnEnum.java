package com.wdjk.webdemo624.constant.log;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-08 23:34
 **/
public enum LogWarnEnum {

    ;
    private Integer errorCode;
    private String errorMessage;

    LogWarnEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
