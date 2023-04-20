package com.dsk.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除请求
 *
 */
@Data
public class DeleteRequest implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    private static final long serialVersionUID = 1L;
}