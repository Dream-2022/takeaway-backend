package com.example.takeawaybackend;

import com.example.takeawaybackend.controller.WebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@MapperScan("com.example.takeawaybackend.dao")
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@EnableWebSocket
public class TakeawayBackendApplication {

	public static void main(String[] args) {
//		SpringApplication.run(TakeawayBackendApplication.class, args);
		SpringApplication springApplication = new SpringApplication(TakeawayBackendApplication.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		WebSocket.setApplicationContext(configurableApplicationContext);

	}

}
