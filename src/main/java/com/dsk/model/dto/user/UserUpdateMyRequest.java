package com.dsk.model.dto.user;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户更新个人信息请求")
public class UserUpdateMyRequest implements Serializable {


    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "简介")
    private String userProfile;

    private static final long serialVersionUID = 1L;
}