package com.example.annotationExample;

public class DemoClass {
	@LogExecutionTime
	public void doSomething() throws InterruptedException {
		System.out.println("Doing something");
		Thread.sleep(500);
	}
	public void doOtherThing() {
		System.out.println("Doing something else");
	}
}