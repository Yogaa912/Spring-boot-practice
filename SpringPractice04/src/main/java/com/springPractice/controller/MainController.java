package com.springPractice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springPractice.Entity.City;
import com.springPractice.service.CityService;

@Controller
@RequestMapping("/city")
public class MainController {
	@Autowired
	private CityService srv;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<City> cities = srv.findAll();
		model.addAttribute("cities", cities);
		return "list";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		City city = srv.findById(id);
		model.addAttribute("city", city);
		return "editPage";
	}
	@PostMapping("/edit")
	public String editSubmit(@ModelAttribute City city) {
		City old = srv.findById(city.getId());
		old.setName(city.getName());
		srv.save(city);
		return "redirect:/city/list";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, Model model) {
		City city = srv.findById(id);
		srv.deleteCity(city);
		return "redirect:/city/list";
	}
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("city", new City());
		return "addPage";
	}
	@PostMapping("/add")
	public String addSubmit(@ModelAttribute City city, Model model) {
		srv.save(city);
		return "redirect:/city/list";
	}
}