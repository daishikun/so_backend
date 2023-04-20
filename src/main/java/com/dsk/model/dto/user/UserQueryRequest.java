package com.dsk.model.dto.user;

import com.dsk.common.PageRequest;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "用户查询请求")
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "开放平台id")
    private String unionId;

    @ApiModelProperty(value = "公众号openId")
    private String mpOpenId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "简介")
    private String userProfile;

    @ApiModelProperty(value = "用户角色：user/admin/ban")
    private String userRole;

    private static final long serialVersionUID = 1L;
}