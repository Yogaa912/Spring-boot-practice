package com.springPractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springPractice.mapper") // <<<<<<<<<<<< 添加这一行，指定你的Mapper接口所在的包
public class Spring071Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring071Application.class, args);
	}
}