package com.springPractice.entity;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 做 ORM 映射
 * 线程安全, 成员属性, 不能被共享
 * 会有很多的属性
 * 
 */

@Component
@Scope("prototype")
public class User {
	@Value("zhangfg")
	private String loginName;
	@Value("12345")
	private String password;
	@Autowired
	private Pet pet;
}