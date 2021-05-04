package com.wdjk.webdemo624;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
*@author zhuhua


 */

@SpringBootApplication
@MapperScan("com.wdjk.webdemo624.mapper")
public class WebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebDemoApplication.class, args);
	}

}
