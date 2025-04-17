package com.springPractice.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.springPractice.entity.User;

/**
 * 一个线程 创建连接
 * 另一个线程 关闭连接
 * 
 * 对应的类 -> Connection 类 所以还是单例的
 * 
 * ThreadLocal
 */

@Component("dao")
public class UserDaoImpl implements UserDao {
	@Autowired
	User user;

	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		System.out.println("get user by name...");
		return user;
	}

}