package com.flouis.controller;

import com.flouis.base.JsonResult;
import com.flouis.base.ResponseCode;
import com.flouis.common.MyConst;
import com.flouis.exception.BusinessException;
import com.flouis.service.SysUserService;
import com.flouis.vo.login.LoginReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户Api")
@Slf4j
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/login")
	@ApiOperation(value = "用户登录")
	public JsonResult login(@RequestBody @Valid LoginReqVo vo) throws BusinessException{
		return JsonResult.success(this.sysUserService.login(vo));
	}

	@PostMapping("/logout")
	@ApiOperation(value = "用户登出")
	public JsonResult logout(HttpServletRequest request) throws BusinessException {
		this.sysUserService.logout(request.getHeader(MyConst.ACCESS_TOKEN), request.getHeader(MyConst.REFRESH_TOKEN));
		return JsonResult.success("登出成功");
	}

	@GetMapping("/toLoginPage")
	@ApiOperation(value = "引导客户端跳转至登录页")
	public JsonResult toLoginPage(){
		return JsonResult.result(ResponseCode.TO_LOGIN);
	}


}
