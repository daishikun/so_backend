package com.dsk.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsk.common.BaseResponse;
import com.dsk.common.ErrorCode;
import com.dsk.common.ResultUtils;
import com.dsk.model.dto.post.PostQueryRequest;
import com.dsk.model.dto.postfavour.PostFavourAddRequest;
import com.dsk.model.dto.postfavour.PostFavourQueryRequest;
import com.dsk.model.entity.Post;
import com.dsk.model.entity.User;
import com.dsk.model.vo.PostVO;
import com.dsk.service.PostFavourService;
import com.dsk.service.PostService;
import com.dsk.service.UserService;
import com.dsk.exception.BusinessException;
import com.dsk.exception.ThrowUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "帖子收藏", tags = {"帖子收藏"})
@Slf4j
@RestController
@RequestMapping("/post_favour")
public class PostFavourController {

    @Resource
    private PostFavourService postFavourService;
    @Resource
    private PostService postService;
    @Resource
    private UserService userService;

    @ApiOperation(value = "收藏 / 取消收藏")
    @PostMapping("/")
    public BaseResponse<Integer> doPostFavour(@RequestBody PostFavourAddRequest postFavourAddRequest,
                                              HttpServletRequest request) {
        if (postFavourAddRequest == null || postFavourAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能操作
        final User loginUser = userService.getLoginUser(request);
        long postId = postFavourAddRequest.getPostId();
        int result = postFavourService.doPostFavour(postId, loginUser);
        return ResultUtils.success(result);
    }

    @ApiOperation(value = "获取我收藏的帖子列表")
    @PostMapping("/my/list/page")
    public BaseResponse<Page<PostVO>> listMyFavourPostByPage(@RequestBody PostQueryRequest postQueryRequest,
                                                             HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postFavourService.listFavourPostByPage(new Page<>(current, size),
                postService.getQueryWrapper(postQueryRequest), loginUser.getId());
        return ResultUtils.success(postService.getPostVOPage(postPage, request));
    }


    @ApiOperation(value = "获取用户收藏的帖子列表")
    @PostMapping("/list/page")
    public BaseResponse<Page<PostVO>> listFavourPostByPage(@RequestBody PostFavourQueryRequest postFavourQueryRequest,
            HttpServletRequest request) {
        if (postFavourQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = postFavourQueryRequest.getCurrent();
        long size = postFavourQueryRequest.getPageSize();
        Long userId = postFavourQueryRequest.getUserId();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20 || userId == null, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postFavourService.listFavourPostByPage(new Page<>(current, size),
                postService.getQueryWrapper(postFavourQueryRequest.getPostQueryRequest()), userId);
        return ResultUtils.success(postService.getPostVOPage(postPage, request));
    }
}
