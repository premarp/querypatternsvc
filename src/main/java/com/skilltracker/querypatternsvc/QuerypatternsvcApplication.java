package com.skilltracker.querypatternsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class QuerypatternsvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuerypatternsvcApplication.class, args);
	}

}
