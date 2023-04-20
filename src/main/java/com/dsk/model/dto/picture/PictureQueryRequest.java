package com.dsk.model.dto.picture;

import com.dsk.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索词")
    private String searchText;

    @ApiModelProperty(value = "搜索类型")
    private String searchType;

}