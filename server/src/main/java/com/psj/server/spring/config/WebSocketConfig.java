package com.psj.server.spring.config;

import com.psj.config.ws.WebSocketServerConfig;
import com.psj.server.spring.interceptor.WebSocketHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @author saiya
 * @date 2018/7/27 0027
 */
@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private WebSocketServerConfig webSocketServerConfig;

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, webSocketServerConfig.getPaths())
                .setAllowedOrigins("*")
                .addInterceptors(webSocketHandshakeInterceptor);
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 文本消息缓存大小，对于tomcat容器来说，每个链接都会有此大小的Frame对象，如果配置太大，会导致内存溢出
        container.setMaxTextMessageBufferSize(webSocketServerConfig.getBufferSize());
        container.setAsyncSendTimeout(webSocketServerConfig.getSendTimeout());
        return container;
    }

}