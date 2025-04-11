package com.example.service;

import com.example.entity.BankAccount;
import com.example.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankService {
	private final BankAccountRepository bankAccountRepository;
	
	public BankService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}
	
	@Transactional  // 事务管理，确保转账要么全部成功，要么全部回滚
	public void transferMoney(String from, String to, double amount) {
		BankAccount sender = getAccount(from);
		BankAccount receiver = getAccount(to);
		
		if(sender.getBalance() < amount) {
			throw new RuntimeException("余额不足，无法转账！");
		}
		
		sender.setBalance(sender.getBalance() - amount);
		bankAccountRepository.save(sender);
		
		// 模拟异常，如果转账金额超过 1000，就触发异常
		if(amount > 1000) {
			throw new RuntimeException("转账金额超出限额，事务回滚！");
		}
		
		receiver.setBalance(receiver.getBalance() + amount);
		bankAccountRepository.save(receiver);
	}
	private BankAccount getAccount(String accountHolder) {
		return bankAccountRepository.findByAccountHolder(accountHolder)
				.orElseThrow(() -> new RuntimeException("账户不存在: " + accountHolder));
	}
}
