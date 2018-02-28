package com.emprovise.service.sqldbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.emprovise.service.sqldbservice.repository")
@SpringBootApplication
public class SqldbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqldbServiceApplication.class, args);
	}
}
