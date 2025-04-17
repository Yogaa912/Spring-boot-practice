package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.*;
import com.example.proxy.*;


@SpringBootApplication
public class TestGetBean {

	public static void main(String[] args) {
		// 动态代理
		UserServiceExecu exe = new UserServiceExecu();
		exe.execuServ();
		// 静态代理
		UserService target2 = new UserServiceImpl();
		UserServiceProxy exe2 = new UserServiceProxy(target2);
		exe2.register();
		
//		SpringApplication.run(TestGetBean.class, args);
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		Car car = new CarFactory().getCar("audi");
//		System.out.println(car.getName());
	}
}