package com.example.proxy;

public class UserServiceImpl implements UserService {
	@Override
	public void register() {
		System.out.println("用户注册逻辑执行成功");
	}
}