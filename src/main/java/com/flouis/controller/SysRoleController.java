package com.flouis.controller;

import com.flouis.base.JsonResult;
import com.flouis.entity.SysRole;
import com.flouis.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
@Api(tags = "角色Api")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping("/all")
	@ApiOperation(value = "查询所有角色")
	public JsonResult all(){
		List<SysRole> all = this.sysRoleService.all();
		return JsonResult.success(all);
	}

}
