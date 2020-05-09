package com.flouis.shiro;

import com.flouis.common.MyConst;
import com.flouis.service.RedisService;
import com.flouis.service.SysPermissionService;
import com.flouis.service.SysRoleService;
import com.flouis.util.jwt.JwtTokenUtil;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description 重写shiro身份认证方法和授权认证方法
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private RedisService redisService;

	/**
	 * @description 判断身份认证token是否为自定义token
	 * @param token
	 * @return
	 */
	@Override
	public boolean supports(AuthenticationToken token){
		return token instanceof CustomUsernamePasswordToken;
	}

	/**
	 * @description 重写身份认证
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		CustomUsernamePasswordToken cupt = (CustomUsernamePasswordToken) authenticationToken;
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(cupt.getPrincipal(), cupt.getCredentials(), CustomRealm.class.getName());
		return info;
	}

	/**
	 * @description 重写授权认证
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String accessToken = (String) principalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		String userId = JwtTokenUtil.getUserId(accessToken);

		/**
		 * 若token剩余时间大于标记key的剩余时间，则该token是在这个标记key之后生成的，反之则在之前生成。
		 * token在标记key之前，可以直接通过JWT解析当前token获取已有的角色/权限数据，否则需要重新查询数据库获取数据。
		 */
		if (redisService.hasKey(MyConst.JWT_REFRESH_KEY + userId) &&
			redisService.getExpire(MyConst.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) >
			JwtTokenUtil.getRemainingTime(accessToken)){

			List<String> roleList = getRoleListByUserId(userId);
			if (roleList != null && roleList.size() > 0){
				info.addRoles(roleList);
			}

			List<String> permissionList = getPermissionListByUserId(userId);
			if (permissionList != null && permissionList.size() > 0){
				info.addStringPermissions(permissionList);
			}
		} else {
			Claims claims = JwtTokenUtil.getClaimsFromToken(accessToken);

			if (claims.get(MyConst.JWT_ROLES) != null){
				info.addRoles((Collection<String>) claims.get(MyConst.JWT_ROLES));
			}

			if (claims.get(MyConst.JWT_PERMISSIONS) != null){
				info.addStringPermissions((Collection<String>) claims.get(MyConst.JWT_PERMISSIONS));
			}
		}
		return info;
	}

	/**
	 * @description 模拟获取角色列表数据
	 */
	private List<String> getRoleListByUserId(String userId){
		List<String> list = Lists.newArrayList();
		if ("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8".equals(userId)){
			list.add("admin");
		} else {
			list.add("test");
		}
		return list;
	}

	/**
	 * @description 模拟获取权限列表数据
	 */
	private List<String> getPermissionListByUserId(String userId){
		List<String> list = Lists.newArrayList();
		if ("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8".equals(userId)){
			list.add("sys:user:add");
			list.add("sys:user:list");
			list.add("sys:user:update");
			list.add("sys:user:detail");
		} else {
			list.add("sys:user:list");
		}
		return list;
	}

}
