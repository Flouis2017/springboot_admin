package com.flouis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TestVo {

	@ApiModelProperty(value = "名称")
	@NotBlank(message = "名称不可为空！")
	private String name;

	@ApiModelProperty(value = "年龄")
	@NotNull(message = "年龄不可为空！")
	private Integer age;

}
