package com.psj.server.websocket.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * web socket 发送消息工具类
 *
 * @author saiya
 * @date 2018/5/24 0024
 */
@Slf4j
public class WebSocketMessageUtil {

    /**
     * 发送消息
     */
    public static void sendMessage(WebSocketSession session, String message) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("socket send fail,sessionId={}, message={}", session.getId(), message, e);
            }

        }
    }

}
