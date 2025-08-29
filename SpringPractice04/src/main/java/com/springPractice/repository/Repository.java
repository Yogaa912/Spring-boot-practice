package com.springPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springPractice.Entity.City;

public interface Repository extends JpaRepository<City, Integer>{
	
}