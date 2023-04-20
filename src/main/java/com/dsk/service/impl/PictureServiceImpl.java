package com.dsk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsk.common.ErrorCode;
import com.dsk.exception.BusinessException;
import com.dsk.model.entity.Picture;
import com.dsk.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author daishikun
 * @date 2023/3/26
 */
@Slf4j
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> queryPicture(String searchText, Integer pageNo, Integer pageSize) {
        int current = (pageNo-1)*pageSize;
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s",searchText,current);
        log.info("url==>{}",url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        for (Element element:elements){
            System.out.println(element);
            if (pictures.size()>=pageSize){
                break;
            }
            String purl = element.select(".iusc .mimg").get(0).attr("src");
            //System.out.println(m);
            System.out.println("===");
            //Map<String,Object> map = JSONUtil.toBean(m,Map.class);

            //String purl = (String) map.get("purl");
            // 标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Picture p = new Picture();
            p.setTitle(title);
            p.setUrl(purl);
            pictures.add(p);
        }
        System.out.println(pictures);
        Page<Picture> picturePage = new Page<>(pageNo,pageSize);
        picturePage.setRecords(pictures);
       return picturePage;
    }
}
