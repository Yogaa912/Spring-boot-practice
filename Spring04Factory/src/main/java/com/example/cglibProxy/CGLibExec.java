package com.example.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibExec {
	private Lemon l;
	public CGLibExec(Lemon l) {
		this.l = l;
	}
	public void proxyExec () {
		/**
		 * 首先有一个 enhancer 
		 * 然后设置代理目标为 对应类 - Lemon
		 */
		Lemon proxyLemon = (Lemon)Enhancer.create(
				Lemon.class, 
				new MethodInterceptor() {
					@Override
	                public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
	                    System.out.println("开始：" + method.getName());
	                    Object result = method.invoke(l, args);
	                    System.out.println("结束：" + method.getName());
	                    return result;
	                }
				}
			);
		proxyLemon.eat();
	}
}