package com.flouis.service;

import com.flouis.dao.SysRoleMapper;
import com.flouis.entity.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleService {

	@Resource
	private SysRoleMapper sysRoleMapper;

	public List<SysRole> all() {
		return this.sysRoleMapper.queryAll();
	}

}
