package com.example.demo.service;

import com.example.demo.common.Result;

public interface WechatService {
    Result<String> login(String code);
    Result<byte[]> getMiniProgramQRCode(String path, Integer width);
}