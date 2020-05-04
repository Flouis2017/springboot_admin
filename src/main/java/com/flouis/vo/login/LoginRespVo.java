package com.flouis.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRespVo {

	@ApiModelProperty(value = "token")
	private String accessToken;

	@ApiModelProperty(value = "刷新的token")
	private String refreshToken;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "用户id")
	private String id;

	@ApiModelProperty(value = "联系方式")
	private String phone;

}
