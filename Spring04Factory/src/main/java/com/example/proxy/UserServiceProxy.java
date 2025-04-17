package com.example.proxy;

public class UserServiceProxy implements UserService {
	private UserService target;
	@Override
	public void register() {
		System.out.println("[日志] 调用 register 方法");
		target.register();
		System.out.println("[日志] register 方法执行完毕");
	}
	public UserServiceProxy(UserService target) {
		this.target = target;
	}
}