package com.flouis.shiro;

import com.alibaba.fastjson.JSON;
import com.flouis.base.JsonResult;
import com.flouis.base.ResponseCode;
import com.flouis.common.MyConst;
import com.flouis.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description 自定义AccessControlFilter——拦截需要认证的请求，首先验证header是否携带了token，如果没有直接
 * 响应客户端，引导客户端到登录界面进行登录操作，如果客户端header携带了token，进入shiro SecurityManager验证
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		log.info("reqUrl: " + request.getRequestURL().toString() + " ==> " + request.getMethod());

		// 判断请求头是否携带了accessToken
		try {
			String accessToken = request.getHeader(MyConst.ACCESS_TOKEN);
			if (StringUtils.isBlank(accessToken)){
				throw new BusinessException(ResponseCode.NULL_TOKEN);
			}
			CustomUsernamePasswordToken cupt = new CustomUsernamePasswordToken(accessToken);
			getSubject(servletRequest, servletResponse).login(cupt);
		} catch (BusinessException be){
			customResponse(be.getExceptionCode(), be.getExceptionMsg(), servletResponse);
			return false;
		} catch (AuthenticationException ae){
			if (ae.getCause() instanceof BusinessException){
				BusinessException exception = (BusinessException) ae.getCause();
				customResponse(exception.getExceptionCode(), exception.getExceptionMsg(), servletResponse);
			} else {
				customResponse(ResponseCode.SHIRO_AUTHENTICATION_ERROR.getCode(),
						ResponseCode.SHIRO_AUTHENTICATION_ERROR.getDescription(), servletResponse);
			}
			return false;
		}

		return true;
	}

	/**
	 * @description 自定义响应
	 */
	private void customResponse(Integer code, String msg, ServletResponse response){
		try {
			JsonResult result = JsonResult.result(code, msg);
			response.setContentType("application/json; charset=utf-8");
			response.setCharacterEncoding("UTF-8");

			String userJson = JSON.toJSONString(result);
			OutputStream os = response.getOutputStream();
			os.write(userJson.getBytes("UTF-8"));
			os.flush();
		} catch (IOException ioe){
			log.error("CustomAccessControlFilter error ==>", ioe);
		}
	}


}
