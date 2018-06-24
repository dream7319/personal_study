package lierl.other.avoid.form.repeat.redis.controller;

import lierl.other.avoid.form.repeat.redis.annotation.CacheLock;
import lierl.other.avoid.form.repeat.redis.annotation.CacheParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 16:08
 **/
@RestController
@RequestMapping("/books")
public class RedisBookController {

	@CacheLock(prefix = "books")
	@GetMapping("/redis")
	public String query(@RequestParam String token){
		return "success-redis-"+token;
	}
}
