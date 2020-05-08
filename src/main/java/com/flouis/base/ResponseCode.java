package com.flouis.base;

public enum ResponseCode {

	SUCCESS(200000, "请求成功"),

	NO_ACCOUNT(400000, "用户不存在"),
	USER_DISABLED(400001, "当前用户不可用"),
	USER_DELETED(400002, "当前用户已被删除"),
	PASSWORD_UNMATCHED(400003, "密码出错"),

	TO_LOGIN(401999, "跳转至登录页"),

	TOKEN_ERROR(401000, "token错误"),
	NULL_TOKEN(401001, "token为空"),
	NEED_AUTHENTICATION(401010, "需要身份认证/登录"),
	TOKEN_EXPIRED(401011, "token过期"),
	NEED_AUTHORIZATION(401012, "需要授权认证"),

	SHIRO_AUTHENTICATION_ERROR(401020, "shiro身份认证出错"),

	REQ_PARAM_EXCEPTION(402001, "请求参数异常，请求失败！"),

	BUSINESS_EXCEPTION(403001, "业务异常，请求失败！"),

	FAIL(500000, "服务器异常，请求失败！");

	private Integer code;
	private String description;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	ResponseCode(Integer code, String description){
		this.setCode(code);
		this.setDescription(description);
	}

}
