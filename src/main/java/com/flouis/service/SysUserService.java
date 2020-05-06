package com.flouis.service;

import com.flouis.base.ResponseCode;
import com.flouis.common.MyConst;
import com.flouis.dao.SysUserMapper;
import com.flouis.entity.SysUser;
import com.flouis.exception.BusinessException;
import com.flouis.util.PageUtil;
import com.flouis.util.jwt.JwtTokenUtil;
import com.flouis.util.jwt.TokenSetting;
import com.flouis.util.password.PasswordUtils;
import com.flouis.vo.PageVo;
import com.flouis.vo.UserVo;
import com.flouis.vo.login.LoginReqVo;
import com.flouis.vo.login.LoginRespVo;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SysUserService {

	@Autowired
	private RedisService redisService;

	@Resource
	private SysUserMapper sysUserMapper;

	/**
	 * @description 登录业务
	 */
	public LoginRespVo login(LoginReqVo vo) throws BusinessException {
		SysUser sysUser = this.sysUserMapper.queryByUsername(vo.getUsername());
		if ( null == sysUser ){
			throw new BusinessException(ResponseCode.NO_ACCOUNT);
		}
		if (sysUser.getState() != 1){
			throw new BusinessException(ResponseCode.USER_DISABLED);
		}
		if (!PasswordUtils.match(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())){
			throw new BusinessException(ResponseCode.PASSWORD_UNMATCHED);
		}
		LoginRespVo respVo = new LoginRespVo();
		respVo.setId(sysUser.getId());
		respVo.setUsername(sysUser.getUsername());
		respVo.setPhone(sysUser.getPhone());

		Map<String, Object> claims = Maps.newHashMap();
		claims.put(MyConst.JWT_PERMISSIONS, getPermissionsByUserId(sysUser.getId()));
		claims.put(MyConst.JWT_ROLES, getRolesByUserId(sysUser.getId()));
		claims.put(MyConst.JWT_USER_NAME, sysUser.getUsername());
		String accessToken = JwtTokenUtil.getAccessToken(sysUser.getId(), claims);
		String refreshToken;
		if (vo.getType().equals(1)){
			refreshToken = JwtTokenUtil.getRefreshToken(sysUser.getId(), claims);

		} else {
			refreshToken = JwtTokenUtil.getRefreshAppToken(sysUser.getId(), claims);
		}
		respVo.setAccessToken(accessToken);
		respVo.setRefreshToken(refreshToken);
		return respVo;
	}

	/**
	 * @description 登出业务
	 */
	public void logout(String accessToken, String refreshToken) throws BusinessException{
		if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(refreshToken)){
			throw new BusinessException(ResponseCode.TOKEN_ERROR);
		}
		Subject subject = SecurityUtils.getSubject();
		log.info("subject.getPrincipals()==>", subject.getPrincipals());
		if (subject.isAuthenticated()){
			subject.logout();
		}
		String userId = JwtTokenUtil.getUserId(accessToken);
		// 把token加入黑名单，禁止再登录
		this.redisService.set(MyConst.JWT_ACCESS_TOKEN_BLACKLIST+accessToken, userId,
				JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
		// 把refreshToken加入黑名单，禁止再拿来刷新token
		this.redisService.set(MyConst.JWT_REFRESH_TOKEN_BLACKLIST+accessToken, userId,
				JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
	}

	/**
	 * @description 模拟获取角色列表
	 */
	private List<String> getRolesByUserId(String userId){
		List<String> list = Lists.newArrayList();
		if("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8".equals(userId)){
			list.add("admin");
		}else{
			list.add("test");
		}
		return  list;
	}

	/**
	 * @description 模拟获取权限列表
	 */
	private List<String>getPermissionsByUserId(String userId){
		List<String> list = Lists.newArrayList();
		if("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8".equals(userId)){
			list.add("sys:user:add");
			list.add("sys:user:list");
			list.add("sys:user:update");
			list.add("sys:user:detail");
		}else{
			list.add("sys:user:list");
		}
		return  list;
	}

	public PageVo userPage(UserVo vo) {
		PageHelper.startPage(vo.getPage(), vo.getSize());
		List<SysUser> sysUserList = this.sysUserMapper.queryAllByVo(vo);
		return PageUtil.getPageVo(sysUserList);
	}

}
