package com.psj.server.websocket.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.MessageHandler;

/**
 * @author saiya
 * @date 2018/7/27 0027
 */
@Slf4j
@Component
public class WebSocketMessageHandler implements MessageHandler.Whole<String> {

    @Override
    public void onMessage(String message) {
        log.info("on message={}", message);
    }

}
