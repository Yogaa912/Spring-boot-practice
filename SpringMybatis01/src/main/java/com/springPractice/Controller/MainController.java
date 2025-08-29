package com.springPractice.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.springPractice.Entity.Account;
import com.springPractice.Entity.Fruits;
import com.springPractice.service.AccountService;
import com.springPractice.service.MenuService;

@RestController
public class MainController {
	@Autowired
	private AccountService accSrv;
	
	@Autowired
	private MenuService menuSrv;
	
	@GetMapping("/list")
	@ResponseBody
	public Object list() {
		List<Account> account = accSrv.findAll();
		List<Fruits> menu = menuSrv.findAll();
		System.out.println();
		return menu;
	}
	@GetMapping("/add")
	@ResponseBody
	public String add() {
		System.out.println("controller");
		accSrv.add();
		return "Ok";
	}
	
	@GetMapping("/addMenu")
	@ResponseBody
	public String addMenu() {
		System.out.println("controller");
		menuSrv.addMenu();
		return "Ok";
	}
	
	@GetMapping("/queryMenu")
	@ResponseBody
	public Object queryMenu(@RequestParam Integer id) {
		System.out.println("queryMenu");
		Fruits f = menuSrv.findById(id);
		return f;
	}
	
	@GetMapping("/page")
	@ResponseBody
	public Object page(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
		System.out.println("queryPage");
		// 分页查询 /page?pageNum=1&pageSize=1
		PageHelper.startPage(pageNum, pageSize);
		// 这里其实 AOP 因为对原有的方法进行了增强
		List<Fruits> list = menuSrv.findByPage(pageNum, pageSize);
		return list;
	}
}