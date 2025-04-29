package com.springPractice.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springPractice.Entity.Fruits;

public interface FruiteRepository extends JpaRepository<Fruits, Long> {
}