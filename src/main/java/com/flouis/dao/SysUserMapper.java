package com.flouis.dao;

import com.flouis.entity.SysUser;
import com.flouis.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    SysUser queryByUsername(@Param("username") String username);

    List<SysUser> queryAllByVo(UserVo vo);
}