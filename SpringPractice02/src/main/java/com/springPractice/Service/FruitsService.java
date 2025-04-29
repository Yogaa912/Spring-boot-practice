package com.springPractice.Service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springPractice.Entity.Fruits;
import com.springPractice.Repository.FruiteRepository;

@Service
public class FruitsService {
	// 业务层需要连接 Repository, 
	@Autowired
	FruiteRepository fruitsRepository;
	
	// 增删改查
	public void deleteFruit(Fruits f) {
		fruitsRepository.delete(f);
	}
	public Fruits addFruit(Fruits f) {
		return fruitsRepository.save(f);
	}
	public void updateFruit(Long id, LocalDate d) {
		Fruits f = fruitsRepository.findById(id).orElseThrow();
		f.setArrivedDate(d);
		fruitsRepository.save(f);
	}
	public List<Fruits> findAll() {
		List<Fruits> findAll = fruitsRepository.findAll();
		return findAll;
	}
	public Fruits findById(Long id) {
		return fruitsRepository.getOne(id);
	}
}