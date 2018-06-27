package lierl.other.avoid.form.repeat.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 锁的参数
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 15:42
 **/
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

	/**
	 * 字段名称
	 * @return
	 */
	String name() default "";
}
