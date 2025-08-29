package com.springPractice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springPractice.Entity.Account;
import com.springPractice.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accSrv;

	@GetMapping("login") 
	public String login() {
		return "account/login";
	}
	
	/**
	 * 用户登录 异步校验
	 * @param loginName
	 * @param password
	 * @return success
	 */
	@RequestMapping("validateAccount")
	@ResponseBody
	public String validateAccount(String loginName, String password, HttpServletRequest request) {
		// 数据校验, 要收集
		System.out.println("loginName:" + loginName);
		System.out.println("password:" + password);
		 
		// 1. 直接返回是否登陆成功的结果
		// 2. 返回 Account 对象, 对象是空的, 在 Controller 里做业务逻辑
		// 内部统一写法
		
		Account acc = accSrv.findByLoginNameAndPassword(loginName, password);
		if(acc == null) {
			return "登录失败";
		} else {
			// 让 service 返回对象, 如果成功登陆, 把用户的对象写入到 session 里面
			// 在不同的 controller 里面, 或者在不同的前端页面上
			// 都能使用当前登陆用户的 Account 对象
			request.getSession().setAttribute("account", acc);
			return "success";
		}
	}
}