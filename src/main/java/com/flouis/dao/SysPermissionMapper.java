package com.flouis.dao;

import com.flouis.entity.SysPermission;

public interface SysPermissionMapper {

    int deleteByPrimaryKey(String id);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

}