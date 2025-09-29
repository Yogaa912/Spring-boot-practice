package com.springPractice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.springPractice.Entity.Account;
import com.springPractice.service.AccountService;
import com.springPractice.service.PermissionService;
import com.springPractice.service.RoleService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	AccountService accountSrv;
	
	@Autowired
	PermissionService permissionSrv;
	
	@Autowired
	RoleService roleSrv;
	
    @GetMapping("accountList")
	public String accountList() {
    	/**
    	PageInfo<Account> page = accountSrv.findByPage(pageNum, pageNum);
    	model.addAttribute("page",page);
    	*/
		System.out.println();
		return "/manager/accountList";
	}
    
    @GetMapping("roleList")
	public String roleList() {
		System.out.println();
		return "/manager/roleList";
	}
    
    @GetMapping("permissionList")
	public String permissionList() {
		System.out.println();
		return "/manager/permissionList";
	}
}