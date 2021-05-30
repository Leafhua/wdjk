package com.wdjk.webdemo624.config;

import com.wdjk.webdemo624.controller.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-09 20:32
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new ApiInterceptor())
                    .addPathPatterns("/api/**");
    }
}
