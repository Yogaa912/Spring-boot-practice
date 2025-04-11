package com.example;
import org.springframework.stereotype.Component;

@Component // 让 Spring 自动管理这个 Bean
public class UserService {
	                      
	// @Component 让 Spring 自动将 UserService 作为 Bean 进行管理。
	public String getUserName() {
        return "Hello, User!";
    }
}
/**
 * Bean 相关代码
 * UserService = Spring bean
 */