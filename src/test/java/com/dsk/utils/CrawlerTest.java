package com.dsk.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dsk.controller.ChatGPT;
import com.dsk.model.entity.Post;
import com.swordintent.chatgpt.ChatgptClient;
import com.swordintent.chatgpt.ChatgptClientImpl;
import com.swordintent.chatgpt.protocol.ChatGptConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author daishikun
 * @date 2023/3/25
 */
@SpringBootTest
public class CrawlerTest {


    @Resource
    private ChatGPT chatGPT;
    @Test
    public void completion() throws Exception {
//        try {
//            chatGPT.completion("介绍下自己");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ChatgptClient chatgptClient = ChatgptClientImpl.getInstance();
        ChatGptConfig chatGptConfig = ChatGptConfig.builder()
                .email("")
                .password("")
                .build();
        String address = "http://127.0.0.1:5000";
        chatgptClient.init(address, chatGptConfig);

    }



    public static void testFeachPassage(String[] args) {

        // 1. 获取数据
        String json = "{\"current\": 1,\"pageSize\": 8,\"sortField\": \"createTime\",\"sortOrder\": \"descend\",\"category\": \"文章\",\"reviewStatus\": 1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
        // System.out.println(result);

        // json 转化对象
        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        System.out.println(map);
        JSONObject data = (JSONObject)map.get("data");
        JSONArray records = (JSONArray)data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object o:records){
           JSONObject tempRecord = (JSONObject)o;
            Post post = new Post();
            post.setId(null);
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray)tempRecord.get("tags");
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

        System.out.println(postList);

    }

    public static void main(String[] args) throws IOException {


    }
}
