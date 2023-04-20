package com.dsk.controller;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author daishikun
 * @date 2023/4/4
 */
@Component
public class ChatGPT {

    private static final String API_KEY = "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";
    private static final MediaType MEDIA_TYPE_JSON  = MediaType.parse("application/json;charset=utf-8");

// python -m revChatGPT --V3 --api_key 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

    @Data
    private static class CompletionRequest {
        private String model = "gpt-3.5-turbo";
        private String prompt;
        private Integer max_tokens = 7;
        private float temperature = 0.5f;
        private Integer top_p = 1;
        private Integer n = 1;
        private Boolean stream = false;
        private Boolean logprobs;
        private String stop;
    }

    public static void main(String[] args) {
        Date date =  new Date(2023, 3, 31);
        System.out.println(date.getTime());
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    public void completion(String prompt) throws IOException {
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setPrompt(prompt);
        Gson gson = new Gson();
        //String reqJson = moshi.adapter(CompletionRequest.class).toJson(completionRequest);
        String reqJson = gson.toJson(completionRequest);
        System.out.println("reqJson: " + reqJson);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                // 将 API_KEY 替换成你自己的 API_KEY
                .header("Authorization", "Bearer " + API_KEY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, reqJson))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }
    }


    // 链接：https://juejin.cn/post/7199293850494091301

    // 链接：https://juejin.cn/post/7176133446639550520

    // 链接：https://juejin.cn/post/7216217118588551229

    // git clone https://github.com/zhayujie/bot-on-anything.git
}
