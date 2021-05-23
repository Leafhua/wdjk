package com.wdjk.webdemo624.exception;


import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.controller.annotation.ApiException;

/**
 * 业务异常
 *      - 仅用在业务层
 *
 * @author Suvan
 */
@ApiException
public class ServiceException extends RuntimeException implements IPrintLog {

   private LogWarnEnum logWarnEnum;

    /**
     * Constructor
     */
    public ServiceException(String message) {
        super(message);
    }

    @Override
    public ServiceException log(LogWarnEnum logWarnEnum) {
        this.logWarnEnum = logWarnEnum;
        return this;
    }

    @Override
    public LogWarnEnum getLog() {
        return logWarnEnum;
    }
}
