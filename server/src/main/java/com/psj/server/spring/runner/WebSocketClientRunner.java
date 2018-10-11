package com.psj.server.spring.runner;

import com.psj.server.websocket.client.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author saiya
 * @date 2018/7/27 0027
 */
@Component
@Order(value = 2)
@Slf4j
public class WebSocketClientRunner implements CommandLineRunner {

    private final WebSocketClient client;

    @Autowired
    public WebSocketClientRunner(WebSocketClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) {
        log.info("web socket start client");
//        client.connectToServer();
    }

}
