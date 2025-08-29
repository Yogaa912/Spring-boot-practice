package com.springPractice.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springPractice.Entity.Account;
import com.springPractice.service.accService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 访问 http://主机名.端口号/context-path/Controller 的 URI/方法的 URI
 * http://localhost:80/boot/user/list
 */
@Controller
@RequestMapping("/account")
public class AccountCotroller {
	/**
	 * 返回 String 文本类型时候会寻找模板文件, 位置在resources/templates
	 */
	@Autowired
	accService accSrv;
	
	@GetMapping("/list")
	public String list(Model map) {
		return "list";
	}
	
	@GetMapping("/login")
	public String login(Model map) {
		
		return "account/login";
	}
	
	@PostMapping("/validateAccount")
	@ResponseBody
	public String validate(String loginName, String password, HttpServletRequest request) {
		// 接受前端传过来的数据, 然后链接后端数据库校验
		System.out.println("--  validate  --");
		Account acc = accSrv.findByLoginNameAndPassword(loginName, password);
		if(acc == null) {
			return "fail";
		} else {
			request.getSession().setAttribute("account", acc);
			return "success";
		}
	}
}