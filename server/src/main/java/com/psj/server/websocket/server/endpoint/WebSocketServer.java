package com.psj.server.websocket.server.endpoint;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author saiya
 * @date 2018/7/27 0027
 */
@Slf4j
@ServerEndpoint(value = "/ws/server")
@Component
public class WebSocketServer {

    private final static Map<String, WsSessionUser> WS_SESSION_USER_MAP = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        WS_SESSION_USER_MAP.put(session.getId(), new WsSessionUser(session));
        log.info("onOpen! sessionId={},the number of clients={}", session.getId(), WS_SESSION_USER_MAP.size());
    }

    @OnClose
    public void onClose(Session session) {
        WS_SESSION_USER_MAP.remove(session.getId());
        log.info("onClose! sessionId={}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("onMessage! message={}", message);
        try {
            while (true) {
                Thread.sleep(1000L);
                WsSessionUser wsSessionUser = getSessionUserBySessionId(session.getId());
                if (wsSessionUser != null) {
                    wsSessionUser.sendMessageByPool(message);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("onError，sessionId={}", session.getId(), error);
    }


    private WsSessionUser getSessionUserBySessionId(String sessionId) {
        return WS_SESSION_USER_MAP.get(sessionId);
    }

}
