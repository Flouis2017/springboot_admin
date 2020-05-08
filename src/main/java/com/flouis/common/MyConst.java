package com.flouis.common;

public class MyConst {

	/**
	 * @description redis 用户名 key
	 */
	public static final String JWT_USER_NAME = "jwt_user_name";

	/**
	 * @description redis 权限 key
	 */
	public static final String JWT_PERMISSIONS = "jwt_permissions";

	/**
	 * @description redis 角色 key
	 */
	public static final String JWT_ROLES = "jwt_roles";

	/**
	 * @description 正常token key
	 */
	public static final String ACCESS_TOKEN = "access_token";
	/**
	 * @description 刷新token key
	 */
	public static final String REFRESH_TOKEN = "refresh_token";

	/**
	 * @description 主动刷新的token key（使用场景，比如修改了用户角色/权限后刷新token）
	 */
	public static final String JWT_REFRESH_KEY = "jwt-refresh-key_";
	/**
	 * @description 标记新的access_token
	 */
	public static final String JWT_REFRESH_IDENTIFICATION = "jwt-refresh-identification_";

	/**
	 * @description refresh_token 主动退出后加入黑名单 key
	 */
	public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt-refresh-token-blacklist_";
	/**
	 * @description access_token 主动退出后加入黑名单 key
	 */
	public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt-access-token-blacklist_";

	/**
	 * @description 用户状态 0禁用 1正常 2删除
	 */
	public static final String USER_STATE = "USER_STATE_";

	/**
	 * @description 部门编码 key
	 */
	public static final String DEPT_CODE = "dept_code";

	/**
	 * @description 用户权鉴缓存 key
	 */
	public static final String AUTHORIZATION_CACHE = "authorization_cache";

}
