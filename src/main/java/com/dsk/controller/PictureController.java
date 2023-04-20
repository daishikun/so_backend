package com.dsk.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsk.common.BaseResponse;
import com.dsk.common.ErrorCode;
import com.dsk.common.ResultUtils;
import com.dsk.exception.ThrowUtils;
import com.dsk.model.dto.picture.PictureQueryRequest;
import com.dsk.model.entity.Picture;
import com.dsk.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Api(value = "图片", tags = {"图片"})
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;


    @ApiOperation(value = "图片管理-分页获取列表",notes = "图片管理-分页获取列表")
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // pictureQueryRequest.setSearchText("刘亦菲");
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.queryPicture(pictureQueryRequest.getSearchText(), (int) current, (int) size);
        return ResultUtils.success(picturePage);
    }


}
