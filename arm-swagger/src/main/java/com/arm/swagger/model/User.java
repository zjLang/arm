package com.arm.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel()
@Data
public class User implements Serializable {
    @ApiModelProperty("id")
    private Integer id = 123;

    @ApiModelProperty("名称")
    private String name = "张三";

    @ApiModelProperty("邮箱")
    private String email = "123@qq.com";

    @ApiModelProperty("密码")
    private String password = "123";

    @ApiModelProperty("年龄")
    private Integer age = 12;

    @ApiModelProperty("角色列表")
    private List<Role> roles;

}
