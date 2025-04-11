package com;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

public class SyncExample {
	public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("example.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // 必须等到文件读取完成后才能继续
        }
        System.out.println("读取完毕");
        reader.close();
        // 异步读取文件
        CompletableFuture.runAsync(() -> {
        	try {
        		String content = Files.readString(Paths.get("example.txt"));
        		System.out.println("File content: " + content);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        });
        System.out.println("File is being read asynchronously...");
    }
}
/**
 * 同步读取文件内容
 * 文件读取是同步的，程序必须等文件读取完成才能继续
 */