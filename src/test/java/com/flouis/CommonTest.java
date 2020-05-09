package com.flouis;

import com.flouis.base.ResponseCode;
import com.flouis.exception.BusinessException;
import com.flouis.util.password.PasswordUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class CommonTest {

	@Test
	public void test(){
		System.out.println(Thread.currentThread().getName());
	}

	@Test
	public void passwordTest(){
		String str = "123456";
		String password = PasswordUtils.encode(str, "324ce32d86224b00a02b");
		System.out.println(password);
	}

	@Test
	public void throwExceptionTest(){
		System.out.println(xx(-1));
	}

	private boolean xx(int x){
		try {
			if (x < 0){
				throw new BusinessException(ResponseCode.FAIL);
			}
		} catch (BusinessException e){
			log.error("Exception ==> " + e.getExceptionCode() + ": " + e.getExceptionMsg());
			return false;
		}
		return true;
	}

	@Test
	public void byteCompareTest(){
		Byte b = 1;
		System.out.println(b.equals((byte)1));
		System.out.println("1".equals(String.valueOf(b)));

		List<String> list = Lists.newArrayList();
		System.out.println(list);
		System.out.println(list.isEmpty());
	}

}
