package com.springPractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.springPractice.mapper")
public class SpringMybatis01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringMybatis01Application.class, args);
	}
}