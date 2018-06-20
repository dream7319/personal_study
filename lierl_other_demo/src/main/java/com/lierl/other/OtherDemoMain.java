package com.lierl.other;

import com.lierl.other.avoid.form.repeat.redis.keys.CacheKeyGenerator;
import com.lierl.other.avoid.form.repeat.redis.keys.LockKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OtherDemoMain {

	public static void main(String[] args) {
		SpringApplication.run(OtherDemoMain.class, args);
	}

	@Bean
	public CacheKeyGenerator cacheKeyGenerator() {
		return new LockKeyGenerator();
	}
}
