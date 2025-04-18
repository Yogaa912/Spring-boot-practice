package com.example.annotationExample;
import java.lang.reflect.Method;

public class AnnotationProcessor {
    public static void main(String[] args) throws Exception {
        DemoClass obj = new DemoClass();
        Method[] methods = DemoClass.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.currentTimeMillis();
                method.invoke(obj); // 执行方法
                long end = System.currentTimeMillis();
                System.out.println("Execution time: " + (end - start) + " ms");
            } else {
                method.invoke(obj);
            }
        }
    }
}