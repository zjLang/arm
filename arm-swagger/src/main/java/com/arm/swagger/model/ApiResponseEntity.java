package com.arm.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: api 通用响应模型
 *
 * @author qujingye
 * @Date: 2020-05-25 11:36
 */
@ApiModel
@Data
public class ApiResponseEntity<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private int rCode;
    /**
     * 描述信息
     */
    @ApiModelProperty(value = "描述信息")
    private String msg;
    /**
     * 数据
     */
    @ApiModelProperty(value = "返回数据")
    private T results;

    public ApiResponseEntity(String msg, T results) {
        this.msg = msg;
        this.results = results;
    }

    /**
     * 设置消息
     *
     * @return
     * @author tianwei
     */
    public ApiResponseEntity<T> results(T results) {
        this.results = results;
        return this;
    }


    public static <T> ApiResponseEntity<T> success(T data) {
        return new ApiResponseEntity("success", data);
    }



}
