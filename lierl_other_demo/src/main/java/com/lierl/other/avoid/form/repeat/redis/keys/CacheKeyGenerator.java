package com.lierl.other.avoid.form.repeat.redis.keys;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * redis key的生成策略 接口，可以自己实现不同的策略
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 15:43
 **/
public interface CacheKeyGenerator {
	/**
	 * 获取AOP参数,生成指定缓存Key
	 *
	 * @param pjp PJP
	 * @return 缓存KEY
	 */
	String getLockKey(ProceedingJoinPoint pjp);
}
