package com.wdjk.webdemo624.exception;


import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.controller.annotation.ApiException;

/**
 * 工具类异常
 *      - 仅用在工具层
 *
 * @author Suvan
 */
@ApiException
public class UtilClassException extends RuntimeException implements IPrintLog {

    private LogWarnEnum logWarnEnum;

    /**
     * Constructor
     */
    public UtilClassException(String message) {
        super(message);
    }

    @Override
    public UtilClassException log(LogWarnEnum logWarnEnum) {
        this.logWarnEnum = logWarnEnum;
        return this;
    }

    @Override
    public LogWarnEnum getLog() {
        return logWarnEnum;
    }
}
