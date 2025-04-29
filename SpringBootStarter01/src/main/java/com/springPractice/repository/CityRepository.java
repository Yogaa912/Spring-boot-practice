package com.springPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springPractice.entity.City;

public interface CityRepository extends JpaRepository<City, Integer>{
}