package com.springPractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springPractice.Entity.Account;
import com.springPractice.Entity.Fruits;
import com.springPractice.Entity.FruitsExample;
import com.springPractice.mapper.FruitsMapper;

@Service
public class MenuService {
	@Autowired
	FruitsMapper mapper;
	
	public List<Fruits> findAll() {
		FruitsExample example = new FruitsExample();
		// example 可以用来拼接 sql 不需要自己去写
		example.createCriteria().andNameEqualTo("Osaka");
		return mapper.selectByExample(example);
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
	public void addMenu() {
		java.sql.Date.valueOf("2025-05-06");
		Fruits f = new Fruits();
		f.setName("Zero");
		f.setArrivedDate(null);
		mapper.insert(f);
	}
	public Fruits findById(Integer id) {
		FruitsExample example = new FruitsExample();
		example.createCriteria().andIdEqualTo(id.longValue());
		List<Fruits> list= mapper.selectByExample(example);
		return list.size() == 1 ? list.get(0) : null;
	}
	public List<Fruits> findByPage(Integer pageNum, Integer pageSize) {
		FruitsExample example = new FruitsExample();
		return mapper.selectByExample(example);
	}
}
