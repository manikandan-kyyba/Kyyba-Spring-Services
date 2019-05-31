package com.kyyba.poc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.kyyba.poc.spring.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class KyybaSpringServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KyybaSpringServicesApplication.class, args);
	}

}
