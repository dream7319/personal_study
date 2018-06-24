package lierl.other.avoid.form.repeat.local.exception.global;

import com.alibaba.fastjson.JSON;
import lierl.other.avoid.form.repeat.local.exception.entity.ErrorResponseEntity;
import lierl.other.avoid.form.repeat.local.exception.custom.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:41
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {
	/**
	 * 定义要捕获的异常 可以多个 @ExceptionHandler({})
	 *
	 * @param e        exception
	 * @param response response
	 * @return 响应结果
	 */
	@ExceptionHandler(CustomException.class)
	public ErrorResponseEntity customExceptionHandler(final Exception e, HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		CustomException exception = (CustomException) e;
		return new ErrorResponseEntity<String>(exception.getCode(), exception.getMessage());
	}

	/**
	 * 捕获  RuntimeException 异常
	 * TODO  如果你觉得在一个 exceptionHandler 通过  if (e instanceof xxxException) 太麻烦
	 * TODO  那么你还可以自己写多个不同的 exceptionHandler 处理不同异常
	 *
	 * @param e        exception
	 * @param response response
	 * @return 响应结果
	 */
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponseEntity runtimeExceptionHandler(final Exception e, HttpServletResponse response) {
		int value = HttpStatus.BAD_REQUEST.value();
		response.setStatus(value);
		if(e instanceof ConstraintViolationException){
			String message = ((ConstraintViolationException) e).getConstraintViolations().stream().map( cv -> cv == null ? "null" : cv.getMessage() )
					.collect(Collectors.joining( ", " ));
			return new ErrorResponseEntity<String>(value, message);
		}else{
			RuntimeException exception = (RuntimeException) e;
			return new ErrorResponseEntity<String>(value, exception.getMessage());
		}
	}

	/**
	 * 通用的接口映射异常处理方
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			return new ResponseEntity<>(new ErrorResponseEntity<String>(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
		}
		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
			log.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
						 + ",信息：" + exception.getLocalizedMessage());
			return new ResponseEntity<>(new ErrorResponseEntity<String>(status.value(), "参数转换失败"), status);
		}

		if(ex instanceof BindException){
			BindException bindException = (BindException) ex;
			List<ObjectError> allErrors = bindException.getAllErrors();
//			String message = allErrors.stream().map(error->error.getDefaultMessage()).collect(Collectors.joining( ", " ));
//			return new ResponseEntity<>(new ErrorResponseEntity(status.value(), message), status);
			return new ResponseEntity<>(new ErrorResponseEntity<Object>(status.value(), allErrors), status);
		}

		return new ResponseEntity<>(new ErrorResponseEntity<String>(status.value(), "参数转换失败"), status);
	}
}
