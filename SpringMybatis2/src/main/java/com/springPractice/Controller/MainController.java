package com.springPractice.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	@GetMapping("/")
	@ResponseBody
	public Object index() {
		System.out.println();
		return "index";
	}
	
	@GetMapping("index")
	@ResponseBody
	public Object index1() {
		System.out.println();
		return "/index";
	}
}