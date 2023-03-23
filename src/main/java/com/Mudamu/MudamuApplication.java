package com.Mudamu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MudamuApplication extends SpringBootServletInitializer{

	/**
	* Main
	* 
	*/
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MudamuApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MudamuApplication.class, args);
	}

}
