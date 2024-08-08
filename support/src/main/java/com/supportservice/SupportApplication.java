package com.supportservice;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
class SupportApplication {



	public static void main(String[] args) {
		SpringApplication.run(SupportApplication.class, args);
	}

}
