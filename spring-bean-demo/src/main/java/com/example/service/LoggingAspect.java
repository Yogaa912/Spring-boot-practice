package com.example.service;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Aspect // 这是一个切面
@Component // 让 Spring 管理它
public class LoggingAspect {
	// Advice 后面接 Pointcut
    @Before("execution(* com.example.service.*.*(..))") // 在 service 包下所有方法执行前运行
    public void logBeforeMethod(JoinPoint joinPoint) { // 使用 JoinPoint 来让日志包含 方法名称
        System.out.println("[AOP LOG] 方法开始执行..." + joinPoint.getSignature().getName());
    }

    @After("execution(* com.example.service.*.*(..))") // 在 service 包下所有方法执行后运行
    public void logAfterMethod(JoinPoint joinPoint) {
        System.out.println("[AOP LOG] 方法执行完成！"  + joinPoint.getSignature().getName());
    }
}