package com.wdjk.webdemo624.exception;


import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.controller.annotation.ApiException;

/**
 * 参数异常
 *      - 针对参数校验
 *
 * @author Suvan
 */
@ApiException
public class ParamsErrorException extends RuntimeException implements IPrintLog {

    /**
     * 日志信息
     */
    private LogWarnEnum logWarnEnum;

    /**
     *  Constructor
     */
    public ParamsErrorException(String message) {
        super(message);
    }

    @Override
    public ParamsErrorException log(LogWarnEnum logWarnEnum) {
        this.logWarnEnum = logWarnEnum;
        return this;
    }

    @Override
    public LogWarnEnum getLog() {
        return logWarnEnum;
    }
}
