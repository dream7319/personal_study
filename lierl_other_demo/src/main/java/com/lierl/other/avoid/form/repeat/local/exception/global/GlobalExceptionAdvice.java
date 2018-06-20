package com.lierl.other.avoid.form.repeat.local.exception.global;

import com.lierl.other.avoid.form.repeat.local.exception.entity.ErrorResponseEntity;
import com.lierl.other.avoid.form.repeat.local.exception.custom.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

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
		return new ErrorResponseEntity(exception.getCode(), exception.getMessage());
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
		RuntimeException exception = (RuntimeException) e;
		return new ErrorResponseEntity(value, exception.getMessage());
	}

	/**
	 * 通用的接口映射异常处理方
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			return new ResponseEntity<>(new ErrorResponseEntity(status.value(), exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
		}
		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
			log.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
						 + ",信息：" + exception.getLocalizedMessage());
			return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
		}
		return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
	}
}
