package com.dsk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsk.model.entity.Picture;

/**
 * @author daishikun
 * @date 2023/3/26
 */
public interface PictureService {

    Page<Picture> queryPicture(String searchText, Integer pageNo, Integer pageSize);
}
