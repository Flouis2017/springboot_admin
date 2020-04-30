package com.flouis.controller;


import com.flouis.base.JsonResult;
import com.flouis.base.ResponseCode;
import com.flouis.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(value = "测试接口")
public class TestController {

	@GetMapping("/business/exception")
	@ApiOperation(value = "测试主动抛出业务异常")
	public JsonResult businessExceptionTest(@RequestParam Integer type) throws BusinessException{
		if (type < 1 || type > 3){
			throw new BusinessException(ResponseCode.BUSINESS_EXCEPTION);
		}
		return JsonResult.success();
	}


}
