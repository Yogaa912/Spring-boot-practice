package com.springPractice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.springPractice.mapper")
public class SpringMybatis02Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringMybatis02Application.class, args);
	}
}