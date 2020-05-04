package com.flouis.controller;


import com.flouis.base.JsonResult;
import com.flouis.base.ResponseCode;
import com.flouis.exception.BusinessException;
import com.flouis.vo.TestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
@Api(tags = "测试Api")
public class TestController {

	@GetMapping("/business/exception")
	@ApiOperation(value = "测试主动抛出业务异常")
	public JsonResult businessExceptionTest(@RequestParam Integer type) throws BusinessException{
		if (type < 1 || type > 3){
			throw new BusinessException(ResponseCode.BUSINESS_EXCEPTION);
		}
		return JsonResult.success();
	}

	@PostMapping("/valid/exception")
	@ApiOperation(value = "测试validation抛出业务异常")
	public JsonResult validExceptionTest(@RequestBody @Valid TestVo vo){
		return JsonResult.success(vo);
	}

}
