package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.*;
import com.example.proxy.*;
import com.example.cglibProxy.*;
import com.example.girlProxy.*;


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
		// 静态代理 2
		Human target3 = new Girl();
		ProxyGirl exe3 = new ProxyGirl(target3);
		exe3.eat();
		// 动态代理 2
		exe.execuGirl();
		// CGLib 动态代理
		CGLibExec exe4 = new CGLibExec(new Lemon());
		exe4.proxyExec();
		// CGLib 动态代理 2
		Lemon exe5 = (Lemon)new CGLibFac(new Lemon()).createProxy();
		exe5.eat();
		
//		SpringApplication.run(TestGetBean.class, args);
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		Car car = new CarFactory().getCar("audi");
//		System.out.println(car.getName());
	}
}