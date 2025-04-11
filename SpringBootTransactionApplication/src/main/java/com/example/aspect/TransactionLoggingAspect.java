package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 声明该类是一个切面
@Component // 让 spring 自动管理这个 Bean
public class TransactionLoggingAspect {
	@AfterReturning("execution(* com.example.service.BankService.transferMoney(..))")
	public void logTransactionSuccess(JoinPoint joinPoint) {
		System.out.println("[AOP LOG] 事务成功: " + joinPoint.getSignature().getName());
	}
	
	@AfterThrowing(value = "execution(* com.example.service.BankService.transferMoney(...))", throwing = "ex")
	public void logTransactionFailure(JoinPoint joinPoint, Exception ex) {
        System.out.println("[AOP LOG] 事务失败: " + joinPoint.getSignature().getName() + "，错误: " + ex.getMessage());
    }
}