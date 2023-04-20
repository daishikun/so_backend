package com.dsk.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsk.common.ErrorCode;
import com.dsk.exception.BusinessException;
import com.dsk.exception.ThrowUtils;
import com.dsk.model.dto.post.PostQueryRequest;
import com.dsk.model.dto.user.UserQueryRequest;
import com.dsk.model.vo.PostVO;
import com.dsk.model.vo.UserVO;
import com.dsk.service.PostService;
import com.dsk.service.UserService;
import com.dsk.model.dto.search.SearchRequest;
import com.dsk.model.entity.Picture;
import com.dsk.model.enums.SearchTypeEnum;
import com.dsk.model.vo.SearchVO;
import com.dsk.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @author daishikun
 * @date 2023/4/17
 */
@Slf4j
@Component
public class SearchFacade {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource
    private PictureService pictureService;

    public SearchVO listPostVOByPage(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {

        String searchText = searchRequest.getSearchText();
        String searchType = searchRequest.getSearchType();
        ThrowUtils.throwIf(StringUtils.isBlank(searchType), ErrorCode.PARAMS_ERROR);
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);

        SearchVO searchVO = new SearchVO();
        if (searchTypeEnum == null) {
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureService.queryPicture(searchText, 1, 10);
                return picturePage;
            });


            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userService.listUserVobyPage(userQueryRequest);
                return userVOPage;
            });

            CompletableFuture.allOf(postTask, userTask, pictureTask).join();

            try {
                Page<PostVO> postVOPage = postTask.get();
                Page<UserVO> userVOPage = userTask.get();
                Page<Picture> picturePage = pictureTask.get();

                searchVO.setPostVOList(postVOPage.getRecords());
                searchVO.setUserVOList(userVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.warn("查询异常");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        }else {
            switch (searchTypeEnum){
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                    searchVO.setPostVOList(postVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.queryPicture(searchText, 1, 10);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVobyPage(userQueryRequest);
                    searchVO.setUserVOList(userVOPage.getRecords());
                    break;
            }
            return searchVO;
        }
    }
}
