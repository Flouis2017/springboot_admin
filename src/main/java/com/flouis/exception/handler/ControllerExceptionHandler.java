package com.flouis.exception.handler;

import com.flouis.base.JsonResult;
import com.flouis.base.ResponseCode;
import com.flouis.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

	/**
	 * @description 处理Validation框架异常——请求参数异常处理
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public JsonResult MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
		log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}",
				e.getBindingResult().getAllErrors(), e);
		List<ObjectError> errorList = e.getBindingResult().getAllErrors();
		return createValidExceptionResp(errorList);
	}

	private JsonResult createValidExceptionResp(List<ObjectError> errorList) {
		for (ObjectError error : errorList){
			log.info("msg={}", error.getDefaultMessage());
		}
		return JsonResult.result(ResponseCode.REQ_PARAM_EXCEPTION.getCode(), errorList.get(0).getDefaultMessage());
	}


}
