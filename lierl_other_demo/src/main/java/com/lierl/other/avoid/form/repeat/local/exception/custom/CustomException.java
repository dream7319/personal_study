package com.lierl.other.avoid.form.repeat.local.exception.custom;

/**
 * 自定义异常
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:43
 **/
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 4564124491192825748L;

	private int code;

	public CustomException() {
		super();
	}

	public CustomException(int code, String message) {
		super(message);
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
