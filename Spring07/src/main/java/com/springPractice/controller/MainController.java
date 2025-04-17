package com.springPractice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springPractice.entity.User;
import com.springPractice.service.MainService;

@Component("mainController")
public class MainController {
	/**
	 * 负责逻辑跳转
	 * 在 web 环境下, 由 Controller 层先接入
	 * @Autowired 自动注入
	 */
	
	@Autowired
	private MainService srv;
	public String list() {
		String loginName = "zhangfg";
		String password = "12345";
		User user = srv.login(loginName,password);
		if(user == null) {
			return "登录失败";
		} else {
			return "登录成功";
		}
	}
	// 传统来说要写 get set for srv
}
