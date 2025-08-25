package com.api.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EcommerceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	
	 @Override
    public void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**")
			         .allowedOrigins("http://localhost:3000")
			         .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			         .allowedHeaders("*")
			         .allowCredentials(true);
    }

}
