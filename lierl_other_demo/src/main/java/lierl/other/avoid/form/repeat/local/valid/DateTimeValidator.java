package lierl.other.avoid.form.repeat.local.valid;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *  日期格式验证
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-21 14:36
 **/
public class DateTimeValidator implements ConstraintValidator<CustomDateTime,String> {

	private CustomDateTime customDateTime;

	/**
	 * 主要用于初始化，它可以获得当前注解的所有属性
	 * @param customDateTime
	 */
	@Override
	public void initialize(CustomDateTime customDateTime) {
		this.customDateTime = customDateTime;
	}

	/**
	 * 进行约束验证的主体方法，其中 value 就是验证参数的具体实例，context 代表约束执行的上下文环境。
	 * @param value
	 * @param context
	 * @return
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
		if(StringUtils.isEmpty(value)){
			return true;
		}

		String format = customDateTime.format();
		if(value.length() != format.length()){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			sdf.parse(value);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
