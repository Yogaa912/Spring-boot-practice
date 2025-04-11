package com.example;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    @Around("execution(* com.example.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        Object result = joinPoint.proceed();  // 执行目标方法

        long executionTime = System.currentTimeMillis() - start;
        System.out.println("执行任务 " + joinPoint.getSignature() + " 执行时间: " + executionTime + "ms");
        
        return result;
    }
}

