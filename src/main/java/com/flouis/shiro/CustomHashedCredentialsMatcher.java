package com.flouis.shiro;

import com.flouis.base.ResponseCode;
import com.flouis.common.MyConst;
import com.flouis.exception.BusinessException;
import com.flouis.service.RedisService;
import com.flouis.util.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

	@Autowired
	private RedisService redisService;

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		CustomUsernamePasswordToken cupt = (CustomUsernamePasswordToken) token;
		String accessToken = String.valueOf(cupt.getPrincipal());
		String userId = JwtTokenUtil.getUserId(accessToken);

		try {
			// 用户状态校验
			if (!redisService.hasKey(MyConst.USER_STATE + userId)) {
				throw new BusinessException(ResponseCode.NEED_AUTHENTICATION);
			}
			String userState = redisService.getString(MyConst.USER_STATE + userId);
			if ("0".equals(userState)){
				throw new BusinessException(ResponseCode.USER_DISABLED);
			}
			if ("2".equals(userState)){
				throw new BusinessException(ResponseCode.USER_DELETED);
			}

			// 判断token是否主动登出
			if (redisService.hasKey(MyConst.JWT_ACCESS_TOKEN_BLACKLIST + accessToken)){
				throw new BusinessException(ResponseCode.TOKEN_ERROR);
			}

			// 判断token是否通过校验
			if (!JwtTokenUtil.isTokenExpired(accessToken)){
				throw new BusinessException(ResponseCode.TOKEN_EXPIRED);
			}

			// 剩余过期时间比较，如果token的剩余时间大于标记key的剩余时间，则这个token是在标记key之后生成的
			if (redisService.hasKey(MyConst.JWT_REFRESH_KEY + userId)){
				if (redisService.getExpire(MyConst.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS)
					> JwtTokenUtil.getRemainingTime(accessToken)){
					throw new BusinessException(ResponseCode.TOKEN_EXPIRED);
				}
			}
		} catch (BusinessException be){
			log.error("doCredentialsMatch error ==>", be);
		}

		return true;
	}

}
