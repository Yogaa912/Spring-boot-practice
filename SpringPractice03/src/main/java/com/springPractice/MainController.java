package com.springPractice;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.springPractice.entity.Account;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@Autowired
	private AccountService accSrv;
	
	@GetMapping("/list")
	@ResponseBody
	public Object list() {
		List<Account> list = accSrv.findAll();
		return list;
	}
	
	@GetMapping("/listone")
	@ResponseBody
	public List<Account> listOne() {
//		return accSrv.findByIdBetween(5);
//		return (List<Account>) accSrv.findByLoginNameAndPassword("123", "123");
		List<Account> account = accSrv.findxxx(1); // int id
		System.out.println("account" + account);
		return account;
	}
	/**
	 * @return
	 * 区分 get 和 post
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/login")
	public String postLogin() {
		return "login";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@PostMapping("/register")
	public String postRegister(HttpServletRequest request, Account account) {
		String loginName = request.getParameter("loginName");
		System.out.println("loginName: " + loginName);
//		System.out.println("account: " + ToStringBuilder.reflectionToString(account));
		RespState state = accSrv.save(account);
		System.out.println("state is " + state);
		request.setAttribute("state", state); // 把状态传给前面
		return "register";
	}
}
