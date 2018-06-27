package lierl.other.avoid.form.repeat.local.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lierl.other.avoid.form.repeat.local.annotation.LocalLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 基于本地缓存来控制表单重复提交
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:19
 **/
@Aspect
@Configuration
public class LocalLockMethodInterceptor {
	private static final Cache<String,Object> CACHES = CacheBuilder.newBuilder()
												.maximumSize(100)//设置最大缓存数
												.expireAfterWrite(5,TimeUnit.SECONDS)//写入数据后5秒钟失效
												.build();

	@Around("execution(public * *(..))&&@annotation(lierl.other.avoid.form.repeat.local.annotation.LocalLock)")
	public Object interceptor(ProceedingJoinPoint pjp){
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();//获取带有注解的方法
		LocalLock lock = method.getAnnotation(LocalLock.class);//获取注解
		String key = getKey(lock.key(),pjp.getArgs());
		if(!StringUtils.isEmpty(key)){
			if(CACHES.getIfPresent(key) != null){
				throw new RuntimeException("请勿重复请求");
			}
			//如果第一次请求，则放入缓存中
			CACHES.put(key,key);
		}

		try {
			return pjp.proceed();
		} catch (Throwable throwable) {
			throw new RuntimeException("服务器异常");
		}finally {
//			CACHES.invalidate(key);//设置key失效
		}
	}

	/**
	 * 	key的生成策略
	 *  根据注解key值来获取缓存中的key
	 * @param key
	 * @param args
	 * @return
	 */
	private String getKey(String key, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			key = key.replace("arg["+i+"]",args[i].toString());
		}
		return key;
	}
}
