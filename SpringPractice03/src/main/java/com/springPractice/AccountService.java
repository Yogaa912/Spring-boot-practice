package com.springPractice;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springPractice.entity.Account;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accRepo;

	public RespState save(Account account) {
		// 返回的是实体类, 对象带回来
		Account entity = accRepo.save(account); 
		return RespState.build(200);
	}
	public List<Account> findAll() {
		List<Account> findAll = accRepo.findAll();
		return findAll;
	}
	public List<Account> findxxx(int id) {
		List<Account> acc = accRepo.findbyxx(id);
		return acc;
	}
	public List<Account> findByIdBetween(int id) {
		return accRepo.findByIdBetween(1, id);
	}
	public Account findByLoginNameAndPassword(String loginName, String password) {
		return accRepo.findByLoginNameAndPassword(loginName, password);
	}
}