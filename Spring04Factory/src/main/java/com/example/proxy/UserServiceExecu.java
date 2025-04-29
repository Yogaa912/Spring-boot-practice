package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.example.girlProxy.*;

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
	public void execuGirl() {
		Human target = new Girl();
		Human proxyInstance2 = (Human)Proxy.newProxyInstance(target.getClass().getClassLoader(),Girl.class.getInterfaces(), new InvocationHandler() {
			public Object invoke(Object proxyObj, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
				if(method.getName().equals("bath")) {
					System.out.println("log starts with bath method...");
					Object result = method.invoke(target, args);
					System.out.println("log ends...");
					return result;
				} else {
					System.out.println("log starts with other method...");
					Object result = method.invoke(target, args);
					System.out.println("log ends...");
					return result;
				}
				
			}
		});
		proxyInstance2.bath();
		proxyInstance2.eat();
		Human proxyInstance = (Human)Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				(proxyObj, method, args) -> {
					System.out.println("[日志] 开始" + method.getName());
					Object result = method.invoke(target, args);
					System.out.println("[日志] 结束 " + method.getName());
					return result;
				}
			);
		proxyInstance.bath();
	}
}