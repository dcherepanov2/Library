package com.example.adminserverbookshopapp;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminServerBookshopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServerBookshopAppApplication.class, args);
	}
}