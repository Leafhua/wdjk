package com.wdjk.webdemo624.exception;


import com.wdjk.webdemo624.constant.log.LogWarnEnum;
import com.wdjk.webdemo624.controller.annotation.ApiException;

/**
 * FTP 异常
 *      - 针对 FTP 服务器的连接与操作
 *
 * @author Suvan
 */
@ApiException
public class FtpException extends RuntimeException implements IPrintLog {

    private LogWarnEnum logWarnEnum;

    /**
     * Constructor
     */
    public FtpException(String message) {
        super(message);
    }

    @Override
    public FtpException log(LogWarnEnum logWarnEnum) {
        this.logWarnEnum = logWarnEnum;
        return this;
    }

    @Override
    public LogWarnEnum getLog() {
        return logWarnEnum;
    }
}
