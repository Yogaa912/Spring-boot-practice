package com.springPractice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.springPractice.Entity.City;
import com.springPractice.repository.Repository;

@Service
public class CityService {
	@Autowired
	private Repository repo;

	public List<City> findAll() {
		return repo.findAll();
	}

	public City findById(Integer id) {
		return repo.getById(id);
	}

	public void deleteCity(City city) {
		repo.delete(city);
	}

	public void editCity(City city) {
		city.setName(null);
		repo.save(city);
	}

	public void save(City city) {
		repo.save(city);
	}
}