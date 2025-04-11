package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.BankAccount;

import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountHolder(String accountHolder);
}

/**
 * 定义数据库操作
 */