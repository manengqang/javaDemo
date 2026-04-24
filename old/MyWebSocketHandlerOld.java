package com.example.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.demo.service.ThriftUserService;
import com.example.demo.thrift.userInfo;

@Component
public class MyWebSocketHandlerOld {

    private static final String CLIENT_EVENT = "message";
    private static final String SERVER_EVENT = "message";

    private final ThriftUserService thriftUserService;

    @Autowired
    public MyWebSocketHandlerOld(SocketIOServer socketIOServer, ThriftUserService thriftUserService) {
        this.thriftUserService = thriftUserService;
        socketIOServer.addConnectListener(
                client -> System.out.println("Socket.IO连接已建立: " + client.getSessionId()));
        socketIOServer.addDisconnectListener(
                client -> System.out.println("Socket.IO连接已关闭: " + client.getSessionId()));
        socketIOServer.addEventListener(CLIENT_EVENT, String.class, (client, payload, ackSender) -> {
            userInfo info = JSON.parseObject(payload, userInfo.class);
            System.out.println("收到消息 - 用户名: " + info.getUsername() + " 年龄: " + info.getAge());

            userInfo responseInfo = new userInfo();
            responseInfo.setUsername(info.getUsername());
            responseInfo.setAge(info.getAge());
            byte[] encryptedResponse = this.thriftUserService.serialize(responseInfo);
            System.out.println("编译后的信息: " + encryptedResponse);
            System.out.println("解密后的信息: " + this.thriftUserService.deserialize(encryptedResponse));
            client.sendEvent(SERVER_EVENT, encryptedResponse);
        });
    }
}