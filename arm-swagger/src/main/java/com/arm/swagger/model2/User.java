package com.arm.swagger.model2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel()
@Data
public class User implements Serializable {
    @ApiModelProperty("id2")
    private Integer id = 123;

    @ApiModelProperty("名称2")
    private String name = "张三";

    @ApiModelProperty("邮箱2")
    private String email = "123@qq.com";

    @ApiModelProperty("密码2")
    private String password = "123";

    @ApiModelProperty("年龄2")
    private Integer age = 12;

}
