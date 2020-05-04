package com.flouis.util.jwt;

import com.flouis.common.MyConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {

	private static String secretKey;
	private static Duration accessTokenExpireTime;
	private static Duration refreshTokenExpireTime;
	private static Duration refreshTokenExpireAppTime;
	private static String issuer;

	public static void setTokenSetting(TokenSetting tokenSetting){
		secretKey = tokenSetting.getSecretKey();
		accessTokenExpireTime = tokenSetting.getAccessTokenExpireTime();
		refreshTokenExpireTime = tokenSetting.getRefreshTokenExpireTime();
		refreshTokenExpireAppTime = tokenSetting.getRefreshTokenExpireAppTime();
		issuer = tokenSetting.getIssuer();
	}

	/**
	 * @description 签发token
	 * @param issuer 签发人
	 * @param subject 代表这个JWT的主体，即它的所有人 一般是用户id
	 * @param claims 存储在JWT里面的信息 一般放些用户的权限/角色信息
	 * @param ttlMillis 有效时间(毫秒)
	 */
	public static String generateToken(String issuer, String subject, Map<String, Object> claims, Long ttlMillis, String secret) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);
		JwtBuilder builder = Jwts.builder();
		if(null!=claims){
			builder.setClaims(claims);
		}
		if (!StringUtils.isEmpty(subject)) {
			builder.setSubject(subject);
		}
		if (!StringUtils.isEmpty(issuer)) {
			builder.setIssuer(issuer);
		}
		builder.setIssuedAt(now);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		builder.signWith(signatureAlgorithm, signingKey);
		return builder.compact();
	}

	/**
	 * @description 生成access_token
	 */
	public static String getAccessToken(String subject, Map<String, Object> claims){
		return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
	}

	/**
	 * @description 生成PC端 refresh_token
	 */
	public static String getRefreshToken(String subject, Map<String, Object> claims){
		return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
	}

	/**
	 * @description 生成APP端 refresh_token
	 */
	public static String getRefreshAppToken(String subject, Map<String, Object> claims){
		return generateToken(issuer, subject, claims, refreshTokenExpireAppTime.toMillis(), secretKey);
	}

	/**
	 * @description 从token中获取数据声明
	 */
	public static Claims getClaimsFromToken(String token){
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
					.parseClaimsJws(token).getBody();
		} catch (Exception e){
			log.error("token error ==>", e);
			claims = null;
		}
		return claims;
	}

	/**
	 * @description 获取用户id
	 */
	public static String getUserId(String token){
		String userId = null;
		try {
			Claims claims = getClaimsFromToken(token);
			userId = claims.getSubject();
		} catch (Exception e){
			log.error("token error ==>", e);
		}
		return userId;
	}

	/**
	 * @description 获取用户名
	 */
	public static String getUsername(String token){
		String username = null;
		try {
			Claims claims = getClaimsFromToken(token);
			username = (String) claims.get(MyConst.JWT_USER_NAME);
		} catch (Exception e){
			log.error("token error ==>", e);
		}
		return username;
	}

	/**
	 * @description 验证token是否过期 true：已过期	false：未过期
	 */
	public static Boolean isTokenExpired(String token){
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e){
			log.error("token error ==>", e);
			return true;
		}
	}

	/**
	 * @description 校验token true：验证通过	false：验证失败
	 */
	public static Boolean validateToken(String token){
		Claims claims = getClaimsFromToken(token);
		return (claims != null && isTokenExpired(token));
	}

	/**
	 * @description 刷新token
	 */
	public static String refreshToken(String token, Map<String, Object> claims){
		String newToken;
		try {
			Claims parsedClaims = getClaimsFromToken(token);
			// 刷新token时如果内容为空代表原先的用户信息不变，所以直接引用上旧token的内容
			if ( null == claims ){
				claims = parsedClaims;
			}
			newToken = generateToken(parsedClaims.getIssuer(), parsedClaims.getSubject(), claims,
					accessTokenExpireTime.toMillis(), secretKey);
		} catch (Exception e){
			newToken = null;
			log.error("token error ==>", e);
		}
		return newToken;
	}

	/**
	 * @description 获取token剩余时间
	 */
	public static long getRemainingTime(String token){
		long result = 0;
		try {
			long nowMills = System.currentTimeMillis();
			result = getClaimsFromToken(token).getExpiration().getTime() - nowMills;
		} catch (Exception e){
			log.error("token error ==>", e);
		}
		return result;
	}

}
