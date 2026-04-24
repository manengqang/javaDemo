package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * Socket.IO 服务端配置。
 */
@Configuration
public class WebSocketConfig {

    @Bean(destroyMethod = "stop")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration configuration =
                new com.corundumstudio.socketio.Configuration();
        configuration.setHostname("0.0.0.0");
        configuration.setPort(9092);
        configuration.setOrigin("*");

        SocketIOServer server = new SocketIOServer(configuration);
        server.start();
        return server;
    }
}