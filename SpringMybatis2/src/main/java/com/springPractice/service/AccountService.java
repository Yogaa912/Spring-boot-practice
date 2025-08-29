package com.springPractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springPractice.Entity.Account;
import com.springPractice.Entity.AccountExample;
import com.springPractice.mapper.AccountMapper;

@Service
public class AccountService {

	@Autowired
	AccountMapper accMapper;
	public Account findByLoginNameAndPassword(String loginName, String password) {
		AccountExample example = new AccountExample();
		example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password);;
		accMapper.selectByExample(example);
		
		/**
		 * 1. 没有, 代表不匹配
		 * 2. 有一条, 代表对了
		 * 3. 好几条, 数据库设置的没有问题就不会出现
		 */
		List<Account> list = accMapper.selectByExample(example);
		return list.size() == 0? null : list.get(0);
	}

}