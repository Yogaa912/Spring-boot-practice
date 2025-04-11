package com.example;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface UserRepositoryInter {
	String getUserName(int userId);
}
class MockUserRepository implements InvocationHandler{
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals("getUserName")) {
			return "Mocked-User-" + args[0];
		}
		return null;
		/**
		 * 这里应该就是拦截方法调用，然后返回准备好的内容，当method名字为getUserName，就return写好的内容
		 * 
		 * 这是 Mockito 背后的基本原理：
		 * Proxy.newProxyInstance() 创建动态代理
		 * invoke() 方法拦截 getUserName() 调用，并返回 Mock 结果
		 * */ 
	}
}

public class DynamicProxyExample {
    public static void main(String[] args) {
        // 3️⃣ 生成 UserRepository 的代理对象
        UserRepositoryInter mockRepo = (UserRepositoryInter) Proxy.newProxyInstance(
            UserRepositoryInter.class.getClassLoader(),
            new Class[]{UserRepositoryInter.class},
            new MockUserRepository()
        );

        // 4️⃣ 调用代理对象的方法
        System.out.println(mockRepo.getUserName(1));  // 输出 "Mocked-User-1"
    }
}