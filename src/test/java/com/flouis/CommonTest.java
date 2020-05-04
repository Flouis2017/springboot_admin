package com.flouis;

import com.flouis.util.password.PasswordUtils;
import org.junit.Test;

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

}
