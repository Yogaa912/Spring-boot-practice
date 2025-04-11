package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootTransactionApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringBootTransactionApplication.class, args);
        BankService bankService = context.getBean(BankService.class);

        System.out.println("测试事务管理 - 正常转账");
        bankService.transferMoney("Alice", "Bob", 500);
        
        System.out.println("\n测试事务管理 - 触发回滚");
        try {
            bankService.transferMoney("Alice", "Bob", 2000);
        } catch (Exception e) {
            System.out.println("异常发生: " + e.getMessage());
        }
    }
}
