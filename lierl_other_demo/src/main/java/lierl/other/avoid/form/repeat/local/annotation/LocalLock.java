package lierl.other.avoid.form.repeat.local.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本地注解
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:16
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {
	String key() default "";

	/**
	 * 设置失效时间
	 * @return
	 */
	int expire() default 5;
}
