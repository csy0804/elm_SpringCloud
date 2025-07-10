package com.neusoft.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.neusoft.po.CommonResult;
import com.sun.tools.javac.Main;
import org.apache.commons.io.IOUtils;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import io.github.jonathanlink.PDFLayoutTextStripper;

@RefreshScope
@RestController
@RequestMapping("/chatgpt")
public class ChatgptController {

   // private static final String OPENAI_API_KEY = "sk-5li3ELj5WUO5QkAjE9AdAcD974524289Aa1f0d084612C5Fe";
    ////注意：这里要换成你自己的OPENAI的代理地址
   // private static final String OPENAI_API_URL = "https://api.xty.app/v1/chat/completions";
    @Value("${spring.ai.openai.api.key}")
    private String OPENAI_API_KEY;

    @Value("${spring.ai.openai.api.url}")
    private String OPENAI_API_URL;
    /*private static final String QUERY = "里面有哪些单词";*/
    private Map<String, List<String>> userSessions = new HashMap<>();
    @PostMapping("/chatgpt")
    public CommonResult<String> chatWithGPT(@RequestBody Map<String,Object> chatRequest)
            throws Exception{
        //获取用户的标识和消息*/
        String userId = chatRequest.get("userId").toString();
        String message = chatRequest.get("message").toString();
        System.out.println(userId+message);
        // 检查用户会话是否存在
        if (!userSessions.containsKey(userId)) {
            //第一次进来肯定不存在会话中，这时候我们放进去
            userSessions.put(userId, new ArrayList<>());
            System.out.println("ok1");
        }
        //把发送的消息扔进这个人的list中
        List<String> sessionMessages = userSessions.get(userId);
        sessionMessages.add(message);
        System.out.println("ok2"+message);
        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

        // 构建请求体
        String requestBody = buildRequestBody(userId, sessionMessages);

        // 发送请求
        RestTemplate restTemplate = new RestTemplate();
        //SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //我用的呆里端口33210，这个要改
       //factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 33210)));
        //restTemplate.setRequestFactory(factory);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL,request, String.class);
        // 提取回复消息
        String responseBody = response.getBody();
        String reply = extractReplyFromResponse(responseBody);
        System.out.println("-------------------"+reply+"--------------------");

        //把回复消息也存进当前用户的的list中，方便上下文记忆
        sessionMessages.add(reply);
        System.out.println(reply);
       // String reply = "124353";
        return new CommonResult(200,"success",reply);
    }



    private static Map<String, String> extractAnswer(String apiResponse) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(apiResponse, Map.class);
        if (!(map.get("choices") instanceof List)) {
            return Collections.emptyMap();
        }
        List<Map<String, Object>> choices = (List<Map<String, Object>>)map.get("choices");
        if (choices.isEmpty() || !choices.get(0).containsKey("text")) {
            return Collections.emptyMap();
        }
        String answer = (String)choices.get(0).get("text");
        return gson.fromJson(answer, Map.class);
    }

    private String buildRequestBody(String userId, List<String> sessionMessages) {
        JSONArray messagesArray = new JSONArray();
        for (String message : sessionMessages) {
            JSONObject messageObj = new JSONObject();
            messageObj.put("role", "user");
            messageObj.put("content", message);
            messagesArray.add(messageObj);
        }

        JSONObject requestBodyObj = new JSONObject();
        requestBodyObj.put("model", "gpt-3.5-turbo");
        requestBodyObj.put("messages", messagesArray);

        return requestBodyObj.toString();
    }

    private String extractReplyFromResponse(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject firstChoice = choices.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        String reply = message.getString("content");

        return reply;
    }

}
