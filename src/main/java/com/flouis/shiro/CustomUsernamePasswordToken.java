package com.flouis.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomUsernamePasswordToken extends UsernamePasswordToken {

	private String token;

	public CustomUsernamePasswordToken(String token){
		this.token = token;
	}

	public Object getPrincipal(){
		return token;
	}

}
