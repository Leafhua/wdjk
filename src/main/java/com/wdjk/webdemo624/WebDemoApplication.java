package com.wdjk.webdemo624;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
*@author zhuhua


 */

@SpringBootApplication
@MapperScan("com.wdjk.webdemo624.mapper")
@ServletComponentScan
public class WebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebDemoApplication.class, args);
	}

}
