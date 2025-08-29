package com.springPractice.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springPractice.Entity.Account;
import com.springPractice.mapper.AccountMapper;

@Service
public class AccountService {
	@Autowired
	AccountMapper mapper;
	public List<Account> findAll() {
		// 原本是用 JPA, 现在用 MyBatis 查询
		return mapper.findAll();
	}
	public void add() {
		Account acc = new Account();
		acc.setAge(19);
		acc.setLocation("Beijing");
		acc.setName("Charlie");
		acc.setPassword("157");
		System.out.println("svr");
		mapper.add(acc);
	}
}