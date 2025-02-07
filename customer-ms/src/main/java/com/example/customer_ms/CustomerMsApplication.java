package com.example.customer_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMsApplication.class, args);
	}

}
