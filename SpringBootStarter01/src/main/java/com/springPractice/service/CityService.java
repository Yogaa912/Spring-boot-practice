package com.springPractice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springPractice.entity.City;
import com.springPractice.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityRepo;
	
	public List<City> findAll() {
		List<City> findAll = cityRepo.findAll();
		return findAll;
	}
	public City findOne(Integer id) {
		return cityRepo.getOne(id);
	}
}