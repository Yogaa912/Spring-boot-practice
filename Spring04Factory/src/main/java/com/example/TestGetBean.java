package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.*;


@SpringBootApplication
public class TestGetBean {

	public static void main(String[] args) {
//		SpringApplication.run(TestGetBean.class, args);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		Car car = new CarFactory().getCar("audi");
		System.out.println(car.getName());
	}

}
