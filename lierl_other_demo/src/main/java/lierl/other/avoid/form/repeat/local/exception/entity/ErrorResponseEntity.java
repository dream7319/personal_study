package lierl.other.avoid.form.repeat.local.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:44
 **/
@Data
@AllArgsConstructor
public class ErrorResponseEntity<T> {
	private int code;
	private T message;
}
