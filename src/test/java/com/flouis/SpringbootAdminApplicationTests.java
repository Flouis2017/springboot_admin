package com.flouis;

import com.flouis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAdminApplicationTests {

	@Autowired
	private RedisService redisService;

	@Test
	public void redisTest() {
		String key = "username";
		String value = "Flouis";
		this.redisService.set(key, value);
		System.out.println("\nredisTest: getExpire() ==> " + this.redisService.getExpire(key, TimeUnit.SECONDS));
		this.redisService.set(key, value, 60L, TimeUnit.MINUTES);
		System.out.println("\nredisTest: getExpire() ==> " + this.redisService.getExpire(key, TimeUnit.SECONDS));

		key = "c501d21144b5479d8cef4b76a8272cdf";
		value = this.redisService.getString(key);
		System.out.println("\nredisTest: getString() ==> " + value + "\n");
	}

}
