package com.example;

import com.example.entity.BankAccount;
import com.example.repository.BankAccountRepository;
import com.example.service.BankService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTransactionApplication implements CommandLineRunner {
	private final BankService bankService;
    private final BankAccountRepository bankAccountRepository;

    public SpringBootTransactionApplication(BankService bankService, BankAccountRepository bankAccountRepository) {
    	this.bankService = bankService;
    	this.bankAccountRepository = bankAccountRepository;
    }
    
    public static void main(String[] args) {
    	SpringApplication.run(SpringBootTransactionApplication.class, args);
    }
    
    @Override
    public void run(String... args) {
    	// 初始化账户数据
    	if (!bankAccountRepository.findByAccountHolder("Alice").isPresent()) {
            bankAccountRepository.save(new BankAccount("Alice", 5000));
        }
        if (!bankAccountRepository.findByAccountHolder("Bob").isPresent()) {
            bankAccountRepository.save(new BankAccount("Bob", 3000));
        }
    	System.out.println("\n 💰 开始正常转账 500...");
    	bankService.transferMoney("Alice", "Bob", 500);
    	
    	System.out.println("\n⚠️ 触发回滚（转账 2000 超出限额）...");
    	try {
    		bankService.transferMoney("Alice", "Bob", 2000);
    	} catch (Exception e) {
    		System.out.println("❌ 异常: " + e.getMessage());
    	}
    }
}
