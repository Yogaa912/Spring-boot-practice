package com.springPractice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@GetMapping("/")
	@ResponseBody
	public Object index() {
		System.out.println();
		return "index";
	}
	
	@GetMapping("index")
	public Object index1() {
		System.out.println();
		return "index";
	}
}