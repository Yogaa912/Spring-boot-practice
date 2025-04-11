package com.example;

@MyComponent
public class UserController {
	@MyAutowired
	private UserService userService;
	public void run() {
		System.out.println();
		userService.sayHello();
	}
}