package com;
public class ConcurrencyExample {
    public static void main(String[] args) {
        Thread task1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 1: " + i);
            }
        });

        Thread task2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Task 2: " + i);
            }
        });
        task1.start();
        task2.start();
    }
}
// 在单核 CPU 上，task1 和 task2 会交替执行，而非同时。