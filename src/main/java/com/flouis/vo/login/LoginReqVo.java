package com.flouis.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginReqVo {

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "登录类型（1：PC  2：App）")
	@NotNull(message = "登录类型不可为空！")
	private Integer type;

}
