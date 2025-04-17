package com.example.proxy;

import java.lang.reflect.Proxy;

public class UserServiceExecu {
	public void execuServ() {
		UserService target = new UserServiceImpl();
		UserService proxy = (UserService)Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				(proxyObj, method, args) -> {
					System.out.println("开始执行" + method.getName());
					Object result = method.invoke(target, args);
					System.out.println("结束执行" + method.getName());
					return result;
				}
			);
		proxy.register();
	}
}