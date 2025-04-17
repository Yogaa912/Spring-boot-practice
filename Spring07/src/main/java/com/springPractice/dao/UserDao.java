package com.springPractice.dao;
import com.springPractice.entity.User;

public interface UserDao {
	public User getUserByName(String name);
}