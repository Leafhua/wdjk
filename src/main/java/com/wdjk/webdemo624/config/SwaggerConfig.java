package com.wdjk.webdemo624.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;


/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-04 17:10
 **/
@Configuration
@EnableOpenApi
@ComponentScan(basePackages = {"com.wdjk.webdemo624.controller"})
public class SwaggerConfig {
    /**
     * 配置Swagger的 Docket的Bean实例
     *
     *
     *RequestHandlerSelectors,配置要扫描的接口的方式
     *  basePackage，指定扫描的包
     *  any():扫描全部
     *  none():不扫描
     *  withMethodAnnotation():扫描方法上的注解
     *  withClassAnnotation():扫描类上的注解，参数是注解的反射对象
     *path()过滤()路径
     */

    @Bean
    public Docket createRestApi1(Environment environment){

        //设置显示swagger的环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        //通过environment.acceptsProfiles判断是否在设置的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo1())
                .groupName("zhuhua")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wdjk.webdemo624.controller"))
                .build();
    }
    @Bean
    public Docket createRestApi2(Environment environment){

        //设置显示swagger的环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        //通过environment.acceptsProfiles判断是否在设置的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                //.apiInfo(apiInfo1())
                .groupName("liboqiao")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wdjk.webdemo624.controller"))
                .build();
    }
    @Bean
    public Docket createRestApi3(Environment environment){

        //设置显示swagger的环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        //通过environment.acceptsProfiles判断是否在设置的环境当中
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                //.apiInfo(apiInfo1())
                .groupName("linting")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wdjk.webdemo624.controller"))
                .build();
    }
    private ApiInfo apiInfo1(){

        //作者信息
        Contact contact = new Contact("zhuhua", "https://zhuhua.icu/", "null");

        return new ApiInfo("无敌女高中生Api文档",
                "API文档",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
