package com.springPractice.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.springPractice.entity.City;
import com.springPractice.service.CityService;

/**
 * 访问 http://主机名.端口号/context-path/Controller 的 URI/方法的 URI
 * http://localhost:80/boot/user/list
 */
@Controller
@RequestMapping("/city")
public class MainCotroller {
	/**
	 * 返回 String 文本类型时候会寻找模板文件, 位置在resources/templates
	 */
	@Autowired
	CityService citySrv;
	
	@GetMapping("/list")
	public String list(Model map) {
		List<City> list = citySrv.findAll();
		map.addAttribute("cities", list);
		return "list";
	}
	@GetMapping("/submit")
	public String getOne(@PathVariable("id") Integer id, Model model) {
		City city = citySrv.findOne(id);
		model.addAttribute("city", city);
		return "list1";
	}
}