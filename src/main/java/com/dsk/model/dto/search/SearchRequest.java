package com.dsk.model.dto.search;

import com.dsk.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@ApiModel(value = "查询请求", description = "查询请求")
@EqualsAndHashCode(callSuper = true)
public class SearchRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "搜索词")
    private String searchText;

    @ApiModelProperty(value = "搜索类型")
    private String searchType;

}