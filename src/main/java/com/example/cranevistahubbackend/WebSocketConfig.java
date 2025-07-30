package com.example.cranevistahubbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置一个或多个简单的消息代理，用于将消息路由到客户端。
        // "/topic" 是一个公共前缀，表示所有目的地以 "/topic" 开头的消息都将路由到消息代理。
        config.enableSimpleBroker("/topic");
        // 定义了客户端发送消息到服务器时，需要使用的应用前缀。
        // 例如，客户端发送消息到 "/app/hello"，该消息将被路由到带有 @MessageMapping("/hello") 注解的方法。
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 STOMP 端点，客户端将使用它来连接到 WebSocket 服务器。
        // "/ws" 是连接的端点 URL。
        // withSockJS() 提供了 SockJS 的支持，以便在浏览器不支持 WebSocket 时提供备用选项。
        // setAllowedOrigins("*") 允许所有来源的跨域连接。
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
