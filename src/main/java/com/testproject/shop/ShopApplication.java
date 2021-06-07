package com.testproject.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {
	public static void main(String[] args) {
		MessageHandler mh = new MessageHandler();
		java.util.Map<String, String> map = mh.getRsContent(mh.tmp_test);
		for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		SpringApplication.run(ShopApplication.class, args);
	}

}
