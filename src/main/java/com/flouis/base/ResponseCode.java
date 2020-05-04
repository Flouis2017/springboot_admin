package com.flouis.base;

public enum ResponseCode {

	SUCCESS(200000, "请求成功"),

	NO_ACCOUNT(400000, "用户不存在"),
	USER_DISABLED(400001, "当前用户不可用"),
	PASSWORD_UNMATCHED(400001, "密码出错"),
	TO_LOGIN(401999, "跳转至登录页"),
	TOKEN_ERROR(401000, "token错误"),
	NEED_AUTHENTICATION(401001, "需要身份认证"),
	TOKEN_EXPIRED(401002, "token过期"),
	NEED_AUTHORIZATION(401003, "需要授权认证"),

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
