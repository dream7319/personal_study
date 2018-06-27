package com.springboot.mybatis.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-26 11:01
 **/
@Data
@NoArgsConstructor
public class ResultBean<T> implements Serializable {
	private static final long serialVersionUid = 1L;
	private static final int SUCCESS = 0;
	private static final int FAIL = 1;
	private String msg = "success";
	private int code = SUCCESS;
	private T data;

	public ResultBean(T data){
		this.data = data;
	}

	public ResultBean(Throwable throwable){
		this.code = FAIL;
		this.msg = throwable.toString();
	}

	public ResultBean(int code,Object msg){
		this.code = code;
		this.msg = msg.toString();
	}
}
