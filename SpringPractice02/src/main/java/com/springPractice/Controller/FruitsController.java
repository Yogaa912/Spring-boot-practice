package com.springPractice.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.springPractice.Entity.Fruits;
import com.springPractice.Service.FruitsService;

@Controller
@RequestMapping("/fruits")
public class FruitsController {
	@Autowired
	private FruitsService FruitServ;
//	public FruitsController(FruitsService fruitServ) {
//		super();
//		FruitServ = fruitServ;
//	}
	// 1. 查询
	@GetMapping("/list")
	public String list(Model map) {
		List<Fruits> list = FruitServ.findAll();
		map.addAttribute("fruits", list);
		return "list";
	}
	// 2. 编辑页面
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Fruits f = FruitServ.findById(id);
		model.addAttribute("fruit", f);
		return "editFruits";
	}
	@PostMapping("/edit")
	// 这里的/edit是链接谁, submit有要怎么连接
	public String editSubmit(@ModelAttribute Fruits f, Model model) {
		FruitServ.addFruit(f);
		model.addAttribute("submittedFruit", f);
		return "result";
	}
	// 3. 显示新增页面
	@GetMapping("/add")
	public String showForm(Model map) {
		map.addAttribute("fruits", new Fruits()); // 表格绑定空对象
		return "addFruits";
	}
	@PostMapping("/add")
	public String addSubmit(@ModelAttribute Fruits f, Model model) {
		FruitServ.addFruit(f);
		model.addAttribute("submittedFruit", f);
		return "result";
	}
	// 4. 删除
	
	public String delete(@PathVariable Long id) {
		FruitServ.deleteFruit(FruitServ.findById(id));
		return "redirect:/fruits/list"; // 这里的return 是什么意思
	}
}