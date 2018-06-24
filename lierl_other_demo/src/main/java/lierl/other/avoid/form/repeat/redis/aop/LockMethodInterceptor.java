package lierl.other.avoid.form.repeat.redis.aop;

import lierl.other.avoid.form.repeat.redis.annotation.CacheLock;
import lierl.other.avoid.form.repeat.redis.keys.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 15:57
 **/
@Aspect
@Configuration
public class LockMethodInterceptor {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private CacheKeyGenerator cacheKeyGenerator;

	@Around("execution(public * *(..))&&@annotation(lierl.other.avoid.form.repeat.redis.annotation.CacheLock)")
	public Object interceptor(ProceedingJoinPoint pjp){
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		CacheLock lock = method.getAnnotation(CacheLock.class);
		if(StringUtils.isEmpty(lock.prefix())){
			throw new RuntimeException("lock key don't null");
		}

		String lockKey = cacheKeyGenerator.getLockKey(pjp);
		try {
			// 采用原生 API 来实现分布式锁
			/**
			 * opsForValue().setIfAbsent(key,value) 它的作用就是如果缓存中没有当前 Key
			 * 则进行缓存同时返回 true 反之亦然；
			 * 当缓存后给 key 在设置个过期时间，防止因为系统崩溃而导致锁迟迟不释放形成死锁
			 */
			final Boolean success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(lockKey.getBytes(), new byte[0], Expiration.from(lock.expire(), lock.timeUnit()), RedisStringCommands.SetOption.SET_IF_ABSENT));
			if (!success) {
				// TODO 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;这里偷下懒
				throw new RuntimeException("请勿重复请求");
			}
			try {
				return pjp.proceed();
			} catch (Throwable throwable) {
				throw new RuntimeException("系统异常");
			}
		} finally {
			// TODO 如果演示的话需要注释该代码;实际应该放开
			// lockRedisTemplate.delete(lockKey);
		}
	}
}
