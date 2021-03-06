package com.flouis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "视图", description = "视图控制器")
@Controller
@RequestMapping("/index")
public class IndexController {

	@GetMapping("/404")
	@ApiOperation("跳转404错误页面")
	public String error404(){
		return "error/404";
	}

	@GetMapping("/login")
	@ApiOperation("跳转登录界面")
	public String login(){
		return "login";
	}

	@GetMapping("/home")
	@ApiOperation("跳转首页界面")
	public String home(){
		return "home";
	}

}
