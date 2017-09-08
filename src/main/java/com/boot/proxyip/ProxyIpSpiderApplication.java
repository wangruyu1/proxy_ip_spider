package com.boot.proxyip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProxyIpSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyIpSpiderApplication.class, args);
	}
}
