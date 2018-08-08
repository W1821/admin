package com.psj.server.websocket.client;

import com.psj.common.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author saiya
 * @date 2018/7/27 0027
 */
@Slf4j
@ClientEndpoint
@Component
public class WebSocketClient extends Endpoint {

    private final static String WEB_SOCKET_SERVER_URL = "ws://localhost:8090/ws/server";

    private Session session;

    private final WebSocketMessageHandler webSocketMessageHandler;


    @Autowired
    public WebSocketClient(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    /**
     * connect to server
     */
    public void connectToServer() {
        log.info("web socket client connect to server...");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        try {
            Session session = container.connectToServer(this, clientEndpointConfig, new URI(WEB_SOCKET_SERVER_URL));
            session.addMessageHandler(webSocketMessageHandler);
        } catch (DeploymentException | IOException e) {
            log.info("reconnect to server after 5000ms...");
            ThreadPoolUtil.sleep(5000L);
            connectToServer();
        } catch (URISyntaxException e) {
            log.error("server url error", e);
        } catch (Exception e) {
            log.error("unknown error", e);
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        log.info("onOpen sessionId={}", session.getId());
        this.session = session;
        sendTextMessage("hello world!");

    }

    @Override
    public void onError(Session session, Throwable t) {
        log.error("onError {}", t);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        log.info("onClose sessionId={}", session.getId());
        connectToServer();
    }

    /**
     * send message
     */
    private void sendTextMessage(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            log.error("send message error", e);
        }
    }


}
