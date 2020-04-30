package com.flouis.controller;

import com.flouis.base.JsonResult;
import com.flouis.entity.SysRole;
import com.flouis.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
@Api(value = "角色接口")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping("/all")
	@ResponseBody
	@ApiOperation(value = "查询所有角色")
	public JsonResult all(){
		List<SysRole> all = this.sysRoleService.all();
		return JsonResult.success(all);
	}

}
