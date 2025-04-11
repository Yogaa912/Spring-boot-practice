package com.example;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMiniIocApplication {
	private Map<Class<?>, Object> beanMap = new HashMap<>();
	public SpringMiniIocApplication(String basePackage) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// 构造器
		// 1. 扫描包路径下所有的类
		List<Class<?>> classList = scanPackage(basePackage);
		// 2. 实例化带 @MyComponent 的类
		for(Class<?> clazz : classList) {
			if(clazz.isAnnotationPresent(MyComponent.class)) {
				Object instance = clazz.getDeclaredConstructor().newInstance();
				beanMap.put(clazz,instance);
			}
		}
		// 3. 注入 @MyAutowired 字段
		for(Object bean : beanMap.values()) {
			Field[] fields = bean.getClass().getDeclaredFields();
			for(Field field : fields) {
				if(field.isAnnotationPresent(MyAutowired.class)) {
					field.setAccessible(true);
					Object dependency = beanMap.get(field.getType()); // 获取字段类型
					field.set(bean, dependency); // 
				}
			}
			
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz) {
		return (T)beanMap.get(clazz);
	}
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, URISyntaxException {
		SpringMiniIocApplication context = new SpringMiniIocApplication("com.example");
		UserController userController = context.getBean(UserController.class);
		userController.run();
	}
	
	private List<Class<?>> scanPackage(String basePackage) throws URISyntaxException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		String path = basePackage.replace('.','/');
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		File directory = new File(url.toURI());
		
		for(File file : directory.listFiles()) {
			if(file.getName().endsWith(".class")) {
				String className = basePackage + "." + file.getName().replace(".class","");
				Class<?> clazz = Class.forName(className);
				classes.add(clazz);
			}
		}
		return classes;
	}

}