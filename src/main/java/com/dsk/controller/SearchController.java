package com.dsk.controller;

import com.dsk.common.BaseResponse;
import com.dsk.common.ResultUtils;
import com.dsk.manager.SearchFacade;

import com.dsk.model.dto.search.SearchRequest;
import com.dsk.model.vo.SearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "聚合搜索", tags = {"聚合搜索"})
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {


    @Resource
    private SearchFacade searchFacade;


    @ApiOperation(value = "聚合搜索", notes = "聚合搜索")
    @PostMapping("/all")
    public BaseResponse<SearchVO> listPostVOByPage(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        SearchVO searchVO = searchFacade.listPostVOByPage(searchRequest, request);
        return ResultUtils.success(searchVO);
    }


}
