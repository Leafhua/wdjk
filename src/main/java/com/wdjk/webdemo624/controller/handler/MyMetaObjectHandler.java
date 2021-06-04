package com.wdjk.webdemo624.controller.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-09 23:47
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.fillStrategy(metaObject,"fuCreatetime",date);
        this.fillStrategy(metaObject,"ftCreatetime",date);
        this.fillStrategy(metaObject,"ftrCreatetime",date);
        this.fillStrategy(metaObject,"ftLastReplytime",date);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();
        this.fillStrategy(metaObject,"ftLastReplytime",date);

    }
}
