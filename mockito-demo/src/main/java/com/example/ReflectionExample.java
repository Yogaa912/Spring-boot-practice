package com.example;
import java.lang.reflect.Method;

public class ReflectionExample {
	public static void main(String[] args) throws Exception {
		// 1. 获取 UserService 的 class 对象，必须使用全限定名（Fully Qualified Name, FQN）= 包名 + 类名
		Class<?> clazz = Class.forName("com.example.UserService2");
		// clazz是通过名字找到的类，这是一个Class<?> type，instance是这个类通过构造器构造的新实例，再通过方法名字来获得这个方法，最后对实例对象调用方法
		
		// 2. 创建 UserService 的实例
		Object instance = clazz.getDeclaredConstructor().newInstance();
		
		// 3. 获取 SayHello 的方法
		Method method = clazz.getMethod("sayHello");
		
		// 4. 在运行期间调用 sayHello 的方法
		method.invoke(instance);
	}
}
