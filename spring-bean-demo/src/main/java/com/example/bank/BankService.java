package com.example.bank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    
    @Transactional  // Spring AOP 事务管理
    public void transferMoney(String from, String to, double amount) {
        withdraw(from, amount); // 扣款
        deposit(to, amount); // 存款
    }

    private void withdraw(String from, double amount) {
        System.out.println("从 " + from + " 账户扣款: " + amount);
        // 模拟异常，测试事务回滚
        if (amount > 1000) {
            throw new RuntimeException("转账金额超出限额，事务回滚！");
        }
    }

    private void deposit(String to, double amount) {
        System.out.println("向 " + to + " 账户存款: " + amount);
    }
}
