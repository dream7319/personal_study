package lierl.other.avoid.form.repeat.redis.keys;

import lierl.other.avoid.form.repeat.redis.annotation.CacheLock;
import lierl.other.avoid.form.repeat.redis.annotation.CacheParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * redis 生成key策略
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 15:44
 **/

public class LockKeyGenerator implements CacheKeyGenerator {
	@Override
	public String getLockKey(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		CacheLock lock = method.getAnnotation(CacheLock.class);
		Object[] args = pjp.getArgs();
		Parameter[] parameters = method.getParameters();
		StringBuilder builder = new StringBuilder();
		// TODO 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
		for (int i = 0; i < parameters.length; i++) {
			CacheParam redisCacheParam = parameters[i].getAnnotation(CacheParam.class);
			if(redisCacheParam == null) {
				continue;
			}
			builder.append(lock.delimiter()).append(redisCacheParam.name());
		}

		if(StringUtils.isEmpty(builder.toString())){
			Annotation[][] annotations = method.getParameterAnnotations();
			for (int i = 0; i < annotations.length; i++) {
				Object arg = args[i];
				Field[] fields = arg.getClass().getDeclaredFields();
				for (Field field : fields) {
					CacheParam cacheParam = field.getAnnotation(CacheParam.class);
					if(cacheParam == null) {
						continue;
					}
					field.setAccessible(true);
					builder.append(lock.delimiter()).append(ReflectionUtils.getField(field,arg));
				}
			}
		}
		return lock.prefix()+builder.toString();
	}
}
