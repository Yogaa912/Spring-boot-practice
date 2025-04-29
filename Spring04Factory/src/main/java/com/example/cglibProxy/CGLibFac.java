package com.example.cglibProxy;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.*;

public class CGLibFac implements MethodInterceptor {
	private Object target;
	public CGLibFac() {}
	public CGLibFac(Object targetObj) {
		this.target = targetObj;
	}
	public Object createProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass()); // 设置父类
		enhancer.setCallback(this);	// 创建代理对象之后去回调, 把包装增强好的属性(里面的属性)传给 intercept
		return enhancer.create();
	}
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("前");
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("后");
		return result;
	}
}