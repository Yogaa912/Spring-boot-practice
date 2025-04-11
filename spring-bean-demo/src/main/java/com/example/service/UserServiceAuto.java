package com.example.service;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAuto {  
	public void createUser() {
        System.out.println("创建用户...");
    }

    public void deleteUser() {
        System.out.println("删除用户...");
    }

}