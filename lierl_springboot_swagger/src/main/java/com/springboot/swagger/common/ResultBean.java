package com.springboot.swagger.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 13:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("返回统一结果")
public class ResultBean<T> {
    @ApiModelProperty("返回编码")
    private int code;
    @ApiModelProperty("返回错误信息")
    private String errorMsg;
    @ApiModelProperty("返回数据")
    private T data;
}
