package com.example.takeawaybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.example.takeawaybackend.dao")
@ServletComponentScan
@SpringBootApplication
public class TakeawayBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakeawayBackendApplication.class, args);
	}

}
