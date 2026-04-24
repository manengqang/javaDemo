package com.example.demo.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.common.Result;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WechatServiceImpl implements WechatService {
    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.secret}")
    private String secret;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public Result<String> login(String code) {
        // 调用微信API获取openid和session_key
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", 
            appId, secret, code);
        
        // 实际项目中应该处理异常和错误情况
        String response = restTemplate.getForObject(url, String.class);
        log.info("微信API响应：{}", response);
        // 这里简单返回微信API的响应，实际项目中应该解析响应并生成自定义token
        return Result.success(response);
    }
    
    @Override
    public Result<byte[]> getMiniProgramQRCode(String path, Integer width) {
        // 获取access_token
        String tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", 
            appId, secret);
        String tokenResponse = restTemplate.getForObject(tokenUrl, String.class);
        
        // 使用fastjson2解析access_token
        JSONObject jsonObject = JSON.parseObject(tokenResponse);
        String accessToken = jsonObject.getString("access_token");
        
        // 调用生成小程序码API
        String qrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken;
        
        // 构建请求参数
        Map<String, Object> params = new HashMap<>();
        
        // 验证path参数格式
        if (path == null || !path.startsWith("/")) {
            throw new IllegalArgumentException("path参数必须以'/'开头");
        }
        
        params.put("path", path);
        params.put("env_version", "develop");
        if (width != null) {
            params.put("width", width);
        }
        
        // 发送POST请求获取小程序码
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 计算请求体长度并设置content-length
        int contentLength = JSON.toJSONString(params).getBytes().length;
        headers.setContentLength(contentLength);
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(params), headers);
        
        try {
            byte[] qrCode = restTemplate.postForObject(qrCodeUrl, request, byte[].class);
            return Result.success(qrCode);
        } catch (Exception e) {
            log.error("生成小程序码失败: {}", e.getMessage(), e);
            throw new RuntimeException("生成小程序码失败: " + e.getMessage(), e);
        }
    }
}