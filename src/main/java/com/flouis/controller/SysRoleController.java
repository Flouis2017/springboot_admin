package com.flouis.controller;

import com.flouis.entity.SysRole;
import com.flouis.service.SysRoleService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/role")
@Api(value = "角色接口")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping("/all")
	@ResponseBody
	@ApiOperation(value = "查询所有角色")
	public Map<String,Object> all(){
		Map<String, Object> resMap = Maps.newHashMap();
		List<SysRole> all = this.sysRoleService.all();
		resMap.put("all", all);
		return resMap;
	}

}
