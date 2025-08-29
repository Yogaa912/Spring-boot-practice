package com.springPractice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springPractice.Entity.Account;

@Mapper
public interface AccountMapper {
	List<Account> findAll();
	void add(Account acc);
}