package com.example;

public class CarFactory {
	public Car getCar(String name) {
		if(name.equals("audi")) {
			return new Audi();
		} else {
			return new Bmw();
		}
	}

}