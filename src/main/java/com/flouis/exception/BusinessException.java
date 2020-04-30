package com.flouis.exception;

import com.flouis.base.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends Exception {

	private Integer exceptionCode;
	private String exceptionMsg;

	public BusinessException(ResponseCode responseCode){
		this(responseCode.getCode(), responseCode.getDescription());
	}

}
