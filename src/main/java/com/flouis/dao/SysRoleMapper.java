package com.flouis.dao;

import com.flouis.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    List<SysRole> queryAll();
}