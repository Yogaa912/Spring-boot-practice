package com;
import java.io.*;
import java.net.*;
import java.net.http.*;

public class SyncNetworkExample {
	public static void main(String[] args) throws IOException{
		
		URL url = new URL("https://www.google.com");
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		while ((line = reader.readLine()) != null) {
            System.out.println(line); // 必须等待请求完成才能输出
        }
        reader.close();
        
        // 异步的网络请求实现
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create("https://example.com"))
                                         .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenAccept(System.out::println);

        System.out.println("Request sent, doing other tasks...");

	}

}
/**
 * 网络请求是阻塞的，程序必须等待服务器响应。
 * 
 * 网络请求是异步的，程序可以继续其他任务，响应结果通过回调处理。
 */