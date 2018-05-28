package com.springcloud.feign.client;

import com.springcloud.feign.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-05-28 15:25
 **/
@FeignClient(name = "lierl-springcloud-user")
public interface UserClient {
	@GetMapping("/user/{id}")
	User getUserById(@PathVariable("id") Integer id);

	@GetMapping("/user/getIpAndPort")
	String getIpAndPort();
}
