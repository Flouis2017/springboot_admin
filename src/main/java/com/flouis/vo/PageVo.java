package com.flouis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PageVo {

	@ApiModelProperty(value = "总计记录数")
	private Long total;

	@ApiModelProperty(value = "总页数")
	private Integer pages;

	@ApiModelProperty(value = "页码")
	private Integer page = 1;

	@ApiModelProperty(value = "每页数")
	private Integer size = 10;

	@ApiModelProperty(value = "当前页记录数")
	private Integer curPageSize;

	@ApiModelProperty(value = "数据集")
	private List<Object> rows;

	@ApiModelProperty(value = "单数据集")
	private Map<String, Object> row;

}
