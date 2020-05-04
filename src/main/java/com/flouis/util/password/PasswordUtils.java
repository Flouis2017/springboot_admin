package com.flouis.util.password;

import java.util.UUID;

/**
* @description  密码工具类
*/
public class PasswordUtils {

	/**
	 * @description 密码匹配
	 */
	public static boolean match(String salt, String rawPass, String encPass) {
		return new PasswordEncoder(salt).matches(encPass, rawPass);
	}
	
	/**
	 * @description 明文密码加密
	 */
	public static String encode(String rawPass, String salt) {
		return new PasswordEncoder(salt).encode(rawPass);
	}

	/**
	 * @description 获取加密盐值
	 */
	public static String getSalt() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
	}
}
