package com;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * 1. 继承 Thread 类
 * 创建一个类，继承 java.lang.Thread
 * 重写 run 方法，定义线程的任务
 * 使用高类创建线程对象并调用 start() 方法启动线程
 * 
 * 适合简单且不需要共享资源的场景
 * 每个线程对象是一个独立的类实例
 */

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println("Thread is running: " + Thread.currentThread().getName());
	}
}
public class ThreadExample {
	public static void main(String[] args) {
		MyThread thread1 = new MyThread();
		MyThread thread2 = new MyThread();
		thread1.start();
		thread2.start();
		// Runnable example
		MyRunnable task = new MyRunnable();
		Thread thread3 = new Thread(task);
		thread3.start();
		/**
		 * 3. 使用 Lambda 表达式
		 * 使用 Lambda表达式简化线程的创建
		 * 简单，适合简单任务，减少样板代码，推荐在轻量级任务中使用
		 */
		Thread thread4 = new Thread(() -> {
			System.out.println("Lambda Thread is running: " + Thread.currentThread().getName());
		});
		Thread thread5 = new Thread(() -> {
			System.out.println("Another Thread is running: " + Thread.currentThread().getName());
		});
		thread4.start();
		thread5.start();
		/**
		 * 4. 线程池 ExecutorService
		 * 更高级的线程管理方式，可以复用线程资源
		 * 更适合高并发场景
		 * 提供线程的生命周期管理
		 */
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Runnable task2 = () -> {
			System.out.println("Thread is running: " + Thread.currentThread().getName());
		};
		executor.execute(task2); // 提交任务
		executor.execute(task2); // 再次提交
		executor.shutdown(); // 关闭线程
	}
}

/** 
 * 2. 实现 Runnable 接口
 * 创建一个类，实现 java.lang.Runnable 接口
 * 实现 Runnable 接口的 run 方法，定义任务
 * 将该类的实例传递给 Thread 对象
 * 调用 start() 方法启动线程
 * 
 * 任务和线程分离，灵活
 * 适合多个线程共享同一个任务的场景
 * 推荐用法，因为Java不支持多继承，所以使用接口 Runnable 可以避免一些限制
 */

class MyRunnable implements Runnable {
	public void run() {
		System.out.println("Runnable Thread is running: " + Thread.currentThread().getName());
	}
}
