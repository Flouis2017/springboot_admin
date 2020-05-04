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
	 * @description refresh_token 主动退出后加入黑名单 key
	 */
	public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt_refresh_token_blacklist";
	/**
	 * @description access_token 主动退出后加入黑名单 key
	 */
	public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt_access_token_blacklist";


}
