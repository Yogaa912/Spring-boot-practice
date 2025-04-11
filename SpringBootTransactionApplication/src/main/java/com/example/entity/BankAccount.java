package com.example.entity;
import jakarta.persistence.*;

@Entity // 声明为 JPA 实体类
@Table(name = "bank_account")
public class BankAccount {
	
	@Id // 这个字段是主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增策略
	private Long id;
	
	@Column(nullable = false, unique = true) // 该字段在数据库中唯一且不能为空
	private String accountHolder;
	
	@Column(nullable = false)
	private double balance; // 余额字段
	
	// 无参数构造函数 JPA 是需要的
	public BankAccount() {}
	
	public BankAccount(String accountHolder, double balance) {
		this.accountHolder = accountHolder;
		this.balance = balance;
	}
	// getter & setter
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
} 
/**
 * @Entity等等，
 * import jakarta.persistence.*;是
 */