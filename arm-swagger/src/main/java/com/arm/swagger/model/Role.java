package com.arm.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "jiaose", description = "角色")
public class Role implements Serializable {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色编码")
    private String code;
}
