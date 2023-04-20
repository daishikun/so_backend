package com.dsk.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dsk.model.entity.Post;
import com.dsk.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author daishikun
 * @date 2023/3/25
 */
@Slf4j
//@Component
public class FeachPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {

        // 1. 获取数据
        String json = "{\"current\": 1,\"pageSize\": 8,\"sortField\": \"createTime\",\"sortOrder\": \"descend\",\"category\": \"文章\",\"reviewStatus\": 1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
        // json 转化对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object o : records) {
            JSONObject tempRecord = (JSONObject) o;
            Post post = new Post();
            post.setId(null);
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setThumbNum(null);
            post.setFavourNum(null);
            post.setUserId(1L);
            post.setCreateTime(null);
            post.setUpdateTime(null);
            post.setIsDelete(0);
            postList.add(post);
        }

        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("保存成功，保存了数据条数为==>{}", postList.size());
        } else {
            log.info("保存失败！！！");
        }
    }
}
