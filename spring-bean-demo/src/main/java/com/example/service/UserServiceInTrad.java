package com.example.service;
import org.springframework.stereotype.Service;

@Service
public class UserServiceInTrad {
	public void createUser() {
        System.out.println("[LOG] 用户创建开始");
        System.out.println("创建用户...");
        System.out.println("[LOG] 用户创建完成");
    }

    public void deleteUser() {
        System.out.println("[LOG] 用户删除开始");
        System.out.println("删除用户...");
        System.out.println("[LOG] 用户删除完成");
    }

}
