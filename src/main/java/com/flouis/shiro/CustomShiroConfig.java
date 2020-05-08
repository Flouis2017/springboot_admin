package com.flouis.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomShiroConfig {

	@Bean
	public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
		return  new CustomHashedCredentialsMatcher();
	}

}
