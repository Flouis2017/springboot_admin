package com.flouis.exception.handler;

import com.flouis.base.JsonResult;
import com.flouis.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	/**
	 * @description 全局异常处理
	 */
	@ExceptionHandler(Exception.class)
	public JsonResult handleException(Exception e){
		log.error("Exception ===>", e);
		return JsonResult.fail();
	}

	/**
	 * @description 业务异常处理
	 */
	@ExceptionHandler(BusinessException.class)
	public JsonResult handleException(BusinessException e){
		log.error("BusinessException ===>", e);
		return JsonResult.result(e.getExceptionCode(), e.getExceptionMsg(), null);
	}

}
