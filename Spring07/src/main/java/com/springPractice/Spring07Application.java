package com.springPractice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.springPractice.controller.MainController;

@SpringBootApplication
public class Spring07Application {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		SpringApplication.run(Spring07Application.class, args);
		MainController controller = ctx.getBean(MainController.class);
        controller.list();
	}

}
