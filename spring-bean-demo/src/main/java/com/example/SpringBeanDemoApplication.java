package com.example;

import com.example.service.UserServiceAuto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication // 让 Spring 扫描并注册所有 Bean
public class SpringBeanDemoApplication {
	public static void main(String[] args) {
//		SpringApplication.run(SpringBeanDemoApplication.class, args);
		
		// 启动 Spring Boot 应用
        ApplicationContext context = SpringApplication.run(SpringBeanDemoApplication.class, args);
        
        // 获取 Spring 管理的 UserService Bean
        UserServiceAuto userServiceAuto = context.getBean(UserServiceAuto.class);
        
        // 测试方法调用
        userServiceAuto.createUser();
        userServiceAuto.deleteUser();
        
        // 获取 UserController Bean
        UserController userController = context.getBean(UserController.class);
        userController.printUserName();
	}

}
/**
 * 创建 Spring Boot 启动类
 * 当你在一个类上加上 @Component 注解时，这个类就被 Spring 框架识别为 一个 Bean，并由 Spring 容器进行管理
 * 
 * 为什么还要额外import
 */