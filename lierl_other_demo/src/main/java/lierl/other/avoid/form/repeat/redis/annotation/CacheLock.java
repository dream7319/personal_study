package lierl.other.avoid.form.repeat.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 11:06
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

	/**
	 * redis 锁 key前缀
	 * @return
	 */
	String prefix() default "";

	/**
	 * 过期时间，默认5秒
	 * @return
	 */
	int expire() default 5;

	/**
	 * 过期时间单位
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * <p>Key的分隔符（默认 :）</p>
	 * <p>生成的Key：N:SO1008:500</p>
	 *
	 * @return String
	 */
	String delimiter() default ":";
}
