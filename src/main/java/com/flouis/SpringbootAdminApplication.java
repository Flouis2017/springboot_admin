package com.flouis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.flouis.dao")
public class SpringbootAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAdminApplication.class, args);
	}

}
