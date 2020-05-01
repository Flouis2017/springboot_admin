package com.flouis.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {

	@ApiModelProperty(value = "请求响应码，200000代表请求成功，其他为失败", name = "code")
	private Integer code;

	@ApiModelProperty(value = "响应消息", name = "msg")
	private String msg;

	@ApiModelProperty(value = "响应数据集", name = "data")
	private Object data;

	public static JsonResult result(Integer code, String msg, Object data){
		JsonResult res = new JsonResult();
		res.setCode(code);
		res.setMsg(msg);
		res.setData(data);
		return res;
	}

	public static JsonResult result(Integer code, String msg){
		return result(code, msg, null);
	}

	public static JsonResult result(ResponseCode responseCode){
		return result(responseCode.getCode(), responseCode.getDescription(), null);
	}

	public static JsonResult success(){
		return result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), null);
	}

	public static JsonResult success(Object data){
		return result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), data);
	}

	public static JsonResult fail(){
		return result(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getDescription(), null);
	}

	public static JsonResult fail(String msg){
		return result(ResponseCode.FAIL.getCode(), msg, null);
	}

	public static JsonResult fail(Integer code, String msg){
		return result(code, msg, null);
	}

}
