package com.dsk.model.vo;

import com.dsk.model.entity.Picture;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author daishikun
 * @date 2023/3/26
 */
@Data
public class SearchVO implements Serializable {

    private List<PostVO> postVOList;

    private List<UserVO> userVOList;

    private List<Picture> pictureList;
}
