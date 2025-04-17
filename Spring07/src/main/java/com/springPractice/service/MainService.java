package com.springPractice.service;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import com.springPractice.dao.UserDao;
import com.springPractice.entity.User;
/**
 * 处理业务逻辑
 * @Component 注册 bean 相当于 <bean id=""> 但是不通过 id, 而是通过类型自动装配
 */

@Component
public class MainService {
	
	// 需要 Dao 了
	@Autowired
	@Qualifier("dao")
	UserDao dao;
	
	public User login(String loginName, String password) {
		System.out.println("loginName:" + loginName);
		System.out.println("Service 接到请求, 开始处理");
		User user = dao.getUserByName(loginName);
		System.out.println(ToStringBuilder.reflectionToString(user));
		return user;
	}
}