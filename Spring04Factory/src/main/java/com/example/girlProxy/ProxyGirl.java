package com.example.girlProxy;

public class ProxyGirl implements Human {
	private Human target;
	@Override
	public void eat() {
		System.out.println("[日志] 开启");
		target.eat();
		System.out.println("[日志] 结束");
	}
	public ProxyGirl(Human target) {
		this.target = target;
	}
	@Override
	public void bath() {
	}
}
