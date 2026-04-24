package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @GetMapping("/login")
    public Result<String> login(@RequestParam String code) {
        return wechatService.login(code);
    }

    @GetMapping("/qrcode")
    public Result<byte[]> getMiniProgramQRCode(@RequestParam String path, @RequestParam(required = false) Integer width) {
        return wechatService.getMiniProgramQRCode(path, width);
    }
}