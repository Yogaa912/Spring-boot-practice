package com.springPractice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springPractice.Entity.Account;
import com.springPractice.mapper.AccountExample;
import com.springPractice.mapper.AccountMapper;

@Service
public class accService {
	@Autowired
	AccountMapper accMapper;

	public List<Account> findAll() {
		AccountExample example = new AccountExample();
		List<Account> list = accMapper.selectByExample(example);
		return list;
	}

	public Account findByLoginNameAndPassword(String loginName, String password) {
		AccountExample example = new AccountExample();
		example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password);
		List<Account> list = accMapper.selectByExample(example);
		return list.size() == 0 ? null : list.get(0);
	}

}
